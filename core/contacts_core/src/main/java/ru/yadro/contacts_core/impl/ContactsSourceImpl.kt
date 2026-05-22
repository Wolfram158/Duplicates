package ru.yadro.contacts_core.impl

import android.content.Context
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
        context.contentResolver.getContacts()
    }
}