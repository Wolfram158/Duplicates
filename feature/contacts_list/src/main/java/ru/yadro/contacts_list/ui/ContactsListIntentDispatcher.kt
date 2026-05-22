package ru.yadro.contacts_list.ui

internal interface ContactsListIntentDispatcher {
    fun dispatch(intent: ContactsListIntent)
}