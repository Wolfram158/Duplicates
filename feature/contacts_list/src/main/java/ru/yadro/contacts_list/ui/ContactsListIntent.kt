package ru.yadro.contacts_list.ui

internal sealed interface ContactsListIntent {
    object DeleteDuplicates : ContactsListIntent
}