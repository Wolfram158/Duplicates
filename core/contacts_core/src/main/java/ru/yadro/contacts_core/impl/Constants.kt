package ru.yadro.contacts_core.impl

import android.provider.ContactsContract

internal object Constants {
    const val SORT_ORDER = "display_name"
    const val SELECTION_ARG1 =
        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?" +
                " and ${ContactsContract.CommonDataKinds.Phone.TYPE} = ?"
}