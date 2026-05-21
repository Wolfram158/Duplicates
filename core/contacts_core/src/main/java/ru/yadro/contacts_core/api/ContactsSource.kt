package ru.yadro.contacts_core.api

interface ContactsSource {
    suspend fun getContacts(): List<Contact>
}