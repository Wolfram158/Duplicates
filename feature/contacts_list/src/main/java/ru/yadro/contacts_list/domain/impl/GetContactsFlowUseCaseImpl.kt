package ru.yadro.contacts_list.domain.impl

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.yadro.contacts_list.di.ContactsListScope
import ru.yadro.contacts_list.domain.api.model.ContactOrLetter
import ru.yadro.contacts_list.domain.api.repository.ContactsListRepository
import ru.yadro.contacts_list.domain.api.usecase.GetContactsFlowUseCase

@SingleIn(ContactsListScope::class)
@ContributesBinding(ContactsListScope::class)
@Inject
internal class GetContactsFlowUseCaseImpl(
    private val repository: ContactsListRepository
) : GetContactsFlowUseCase {
    override fun invoke(): Flow<List<ContactOrLetter>> = repository
        .getContactsFlow()
        .map {
            it.getContactOrLetterList()
        }
}

