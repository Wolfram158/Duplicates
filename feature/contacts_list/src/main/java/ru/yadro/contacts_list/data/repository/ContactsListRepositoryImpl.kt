package ru.yadro.contacts_list.data.repository

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import ru.yadro.contacts_core.api.Contact
import ru.yadro.contacts_core.api.ReactiveContactsSource
import ru.yadro.contacts_list.di.ContactsListScope
import ru.yadro.contacts_list.domain.api.repository.ContactsListRepository

@SingleIn(ContactsListScope::class)
@ContributesBinding(ContactsListScope::class)
@Inject
internal class ContactsListRepositoryImpl(
    private val reactiveContactsSource: ReactiveContactsSource
) : ContactsListRepository {
    override fun getContactsFlow(): Flow<List<Contact>> = reactiveContactsSource.getContactsFlow()
}