package ru.yadro.contacts_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.yadro.contacts_core.api.usecase.BindServiceUseCase
import ru.yadro.contacts_core.api.usecase.DeleteDuplicatesUseCase
import ru.yadro.contacts_core.api.usecase.UnbindServiceUseCase
import ru.yadro.contacts_list.di.ContactsListScope
import ru.yadro.contacts_list.domain.api.usecase.GetContactsFlowUseCase

@Suppress("UNCHECKED_CAST")
@SingleIn(ContactsListScope::class)
@Inject
internal class ContactsListViewModelFactory(
    private val getContactsFlowUseCase: GetContactsFlowUseCase,
    private val deleteDuplicatesUseCase: DeleteDuplicatesUseCase,
    private val bindServiceUseCase: BindServiceUseCase,
    private val unbindServiceUseCase: UnbindServiceUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(ContactsListViewModel::class.java)) {
            throw RuntimeException("Expected: ${ContactsListViewModel::class}, given: $modelClass")
        }
        return ContactsListViewModel(
            getContactsFlowUseCase = getContactsFlowUseCase,
            deleteDuplicatesUseCase = deleteDuplicatesUseCase,
            bindServiceUseCase = bindServiceUseCase,
            unbindServiceUseCase = unbindServiceUseCase,
            ioDispatcher = ioDispatcher
        ) as T
    }
}