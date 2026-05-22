package ru.yadro.contacts_core.impl

import android.content.ContentResolver
import android.content.ContentUris
import android.database.ContentObserver
import android.net.Uri
import android.provider.ContactsContract
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import ru.yadro.contacts_core.api.Contact

internal fun ContentResolver.register(uri: Uri) = callbackFlow {
    val observer = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {
            trySend(selfChange)
        }
    }

    registerContentObserver(uri, true, observer)

    awaitClose {
        unregisterContentObserver(observer)
    }
}

internal fun ContentResolver.getContacts(): List<Contact> {
    val contacts = mutableListOf<Contact>()
    val projection =
        arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
        )
    query(
        ContactsContract.Contacts.CONTENT_URI, projection,
        null,
        null,
        Constants.SORT_ORDER
    ).use { cursor1 ->
        if (cursor1 == null) {
            return@use
        }
        while (cursor1.moveToNext()) {
            val id =
                cursor1.getLong(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
            val name =
                cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
            var phone: String? = null
            query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
                Constants.SELECTION_ARG1,
                arrayOf(
                    id.toString(),
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE.toString()
                ),
                null
            ).use { cursor2 ->
                cursor2?.let {
                    if (cursor2.moveToNext()) {
                        phone = cursor2.getString(
                            cursor2.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        )
                    }
                }
            }
            contacts.add(Contact(id = id, name = name, mainPhone = phone))
        }
    }
    return contacts
}

internal fun List<Contact>.getDuplicates(): List<Long> {
    return filter { it.mainPhone != null }
        .groupBy { Pair(it.name, it.mainPhone) }
        .values
        .filter { it.size >= 2 }
        .toList()
        .flatMap { group -> group.map { contact -> contact.id } }
}

internal fun ContentResolver.deleteDuplicates(ids: List<Long>) {
    ids.forEach { deleteDuplicate(it) }
}

internal fun ContentResolver.deleteDuplicate(id: Long) {
    val deleteUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id)
    delete(deleteUri, null, null)
}