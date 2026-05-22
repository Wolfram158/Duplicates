package ru.yadro.contacts_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.yadro.contacts_core.api.DeleteDuplicatesResult
import ru.yadro.contacts_core.api.usecase.BindServiceUseCase
import ru.yadro.contacts_core.api.usecase.DeleteDuplicatesUseCase
import ru.yadro.contacts_core.api.usecase.UnbindServiceUseCase
import ru.yadro.contacts_list.domain.api.usecase.GetContactsFlowUseCase

internal class ContactsListViewModel(
    getContactsFlowUseCase: GetContactsFlowUseCase,
    private val deleteDuplicatesUseCase: DeleteDuplicatesUseCase,
    bindServiceUseCase: BindServiceUseCase,
    private val unbindServiceUseCase: UnbindServiceUseCase,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel(), ContactsListIntentDispatcher {
    val contacts = getContactsFlowUseCase()
        .map {
            it.toContactsListState()
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, ContactsListState.Loading)

    private val _events = Channel<ContactsListScreenEvent>(Channel.CONFLATED)
    val events = _events.receiveAsFlow()

    init {
        bindServiceUseCase()
    }

    override fun dispatch(intent: ContactsListIntent) {
        when (intent) {
            ContactsListIntent.DeleteDuplicates -> handleDeleteDuplicates()
        }
    }

    private fun handleDeleteDuplicates() {
        viewModelScope.launch(ioDispatcher) {
            _events.send(
                when (
                    deleteDuplicatesUseCase()) {
                    DeleteDuplicatesResult.Deleted -> ContactsListScreenEvent.Deleted
                    DeleteDuplicatesResult.Error -> ContactsListScreenEvent.Error
                    DeleteDuplicatesResult.NoDuplicates -> ContactsListScreenEvent.NoDuplicates
                }
            )
        }
    }

    override fun onCleared() {
        unbindServiceUseCase()
        super.onCleared()
    }
}