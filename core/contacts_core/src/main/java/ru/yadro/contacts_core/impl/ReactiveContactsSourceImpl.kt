package ru.yadro.contacts_core.impl

import android.content.Context
import android.provider.ContactsContract
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transformLatest
import ru.yadro.contacts_core.api.Contact
import ru.yadro.contacts_core.api.ContactsSource
import ru.yadro.contacts_core.api.ReactiveContactsSource
import ru.yadro.contacts_core.di.ContactsCoreScope

@OptIn(ExperimentalCoroutinesApi::class)
@SingleIn(ContactsCoreScope::class)
@ContributesBinding(ContactsCoreScope::class)
@Inject
internal class ReactiveContactsSourceImpl(
    private val context: Context,
    private val contactsSource: ContactsSource
) : ReactiveContactsSource {
    override fun getContactsFlow(): Flow<List<Contact>> = context
        .contentResolver
        .register(ContactsContract.Contacts.CONTENT_URI)
        .onStart {
            emit(false)
        }
        .transformLatest {
            emit(contactsSource.getContacts())
        }
}