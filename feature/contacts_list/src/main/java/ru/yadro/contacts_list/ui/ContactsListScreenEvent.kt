package ru.yadro.contacts_list.ui

internal sealed interface ContactsListScreenEvent {
    object Deleted : ContactsListScreenEvent
    object Error : ContactsListScreenEvent
    object NoDuplicates : ContactsListScreenEvent
}