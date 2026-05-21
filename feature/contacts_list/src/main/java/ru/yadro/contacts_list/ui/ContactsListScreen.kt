package ru.yadro.contacts_list.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.yadro.common.ui.Loading
import ru.yadro.common.ui.LocalAppComponent
import ru.yadro.contacts_list.di.createContactsListGraph
import ru.yadro.contacts_list.domain.api.model.ContactOrLetter

@Composable
fun ContactsListScreen(
    modifier: Modifier = Modifier
) {
    val appComponent = LocalAppComponent.current
    val graph = remember(appComponent) { appComponent.createContactsListGraph() }
    val viewModelFactory = remember(graph) { graph.contactsListViewModelFactory }
    val viewModel = viewModel<ContactsListViewModel>(factory = viewModelFactory)
    val contacts = viewModel.contacts.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { paddingValues ->
        when (val contacts = contacts.value) {
            ContactsListState.Empty ->
                EmptyContactsList()

            ContactsListState.Loading ->
                Loading()

            is ContactsListState.NonEmpty -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(contacts.contacts, key = { it.key }) { item ->
                        when (item) {
                            is ContactOrLetter.Contact -> ContactItem(item)
                            is ContactOrLetter.Letter -> LetterItem(item)
                        }
                    }
                }
            }
        }
    }
}