package ru.yadro.contacts_list.ui

import android.content.ContentUris
import android.net.Uri
import android.provider.ContactsContract

internal fun Long.getContactPhotoUri() = Uri.withAppendedPath(
    ContentUris.withAppendedId(
        ContactsContract.Contacts.CONTENT_URI,
        this
    ), ContactsContract.Contacts.Photo.CONTENT_DIRECTORY
)