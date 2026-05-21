package ru.yadro.contacts_list.domain.api.repository

import kotlinx.coroutines.flow.Flow
import ru.yadro.contacts_core.api.Contact

internal interface ContactsListRepository {
    fun getContactsFlow(): Flow<List<Contact>>
}