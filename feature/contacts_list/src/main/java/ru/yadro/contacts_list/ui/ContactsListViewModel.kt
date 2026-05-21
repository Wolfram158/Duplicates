package ru.yadro.contacts_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.yadro.contacts_list.domain.api.usecase.GetContactsFlowUseCase

internal class ContactsListViewModel(
    getContactsFlowUseCase: GetContactsFlowUseCase,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    val contacts = getContactsFlowUseCase()
        .map {
            it.toContactsListState()
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, ContactsListState.Loading)
}