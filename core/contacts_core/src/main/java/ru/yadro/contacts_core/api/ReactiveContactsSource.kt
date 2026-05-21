package ru.yadro.contacts_core.api

import kotlinx.coroutines.flow.Flow

interface ReactiveContactsSource {
    fun getContactsFlow(): Flow<List<Contact>>
}