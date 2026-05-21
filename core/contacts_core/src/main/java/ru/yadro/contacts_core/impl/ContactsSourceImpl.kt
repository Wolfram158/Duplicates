package ru.yadro.contacts_core.impl

import android.content.Context
import android.provider.ContactsContract
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.yadro.contacts_core.api.Contact
import ru.yadro.contacts_core.api.ContactsSource
import ru.yadro.contacts_core.di.ContactsCoreScope

@SingleIn(ContactsCoreScope::class)
@ContributesBinding(ContactsCoreScope::class)
@Inject
internal class ContactsSourceImpl(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ContactsSource {
    override suspend fun getContacts(): List<Contact> = withContext(ioDispatcher) {
        val contacts = mutableListOf<Contact>()
        val projection =
            arrayOf(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
            )
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI, projection,
            null,
            null,
            SORT_ORDER
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
                context.contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
                    SELECTION_ARG1,
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
        contacts
    }

    companion object {
        private const val SORT_ORDER = "display_name"
        private const val SELECTION_ARG1 =
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?" +
                    " and ${ContactsContract.CommonDataKinds.Phone.TYPE} = ?"
    }
}