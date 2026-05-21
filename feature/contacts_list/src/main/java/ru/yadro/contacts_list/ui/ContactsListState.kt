package ru.yadro.contacts_list.ui

import ru.yadro.contacts_list.domain.api.model.ContactOrLetter

internal sealed interface ContactsListState {
    class NonEmpty(val contacts: List<ContactOrLetter>) : ContactsListState

    object Empty : ContactsListState

    object Loading : ContactsListState
}

internal fun List<ContactOrLetter>.toContactsListState() = when (isEmpty()) {
    true -> ContactsListState.Empty
    else -> ContactsListState.NonEmpty(this)
}