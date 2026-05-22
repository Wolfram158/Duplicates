package ru.yadro.contacts_list.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.yadro.common.ui.Loading
import ru.yadro.common.ui.LocalAppComponent
import ru.yadro.contacts_list.di.createContactsListGraph

@Composable
fun ContactsListScreen(
    modifier: Modifier = Modifier
) {
    val appComponent = LocalAppComponent.current
    val graph = remember(appComponent) { appComponent.createContactsListGraph() }
    val viewModelFactory = remember(graph) { graph.contactsListViewModelFactory }
    val viewModel = viewModel<ContactsListViewModel>(factory = viewModelFactory)
    val contacts = viewModel.contacts.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }

    ObserveEvents(
        events = viewModel.events,
        snackbar = snackbar
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbar)
        }
    ) { paddingValues ->
        when (val contacts = contacts.value) {
            ContactsListState.Empty ->
                EmptyContactsList(Modifier.padding(paddingValues))

            ContactsListState.Loading ->
                Loading(Modifier.padding(paddingValues))

            is ContactsListState.NonEmpty ->
                NonEmptyContactsList(
                    contacts = contacts.contacts,
                    onDeleteDuplicateContacts = {
                        viewModel.dispatch(ContactsListIntent.DeleteDuplicates)
                    },
                    modifier = Modifier.padding(paddingValues)
                )
        }
    }
}