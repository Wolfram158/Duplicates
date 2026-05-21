package ru.yadro.contacts_core.di

import dev.zacsweers.metro.createGraphFactory
import ru.yadro.contacts_core.api.ContactsSource
import ru.yadro.contacts_core.api.ReactiveContactsSource
import ru.yadro.di.AppComponent

interface ContactsCoreComponent {
    val reactiveContactsSource: Lazy<ReactiveContactsSource>
    val contactsSource: Lazy<ContactsSource>
}

fun AppComponent.createContactsCoreComponent(): ContactsCoreComponent {
    val graph = createGraphFactory<ContactsCoreGraph.Factory>().create(context)
    return object : ContactsCoreComponent {
        override val reactiveContactsSource: Lazy<ReactiveContactsSource> =
            graph.reactiveContactsSource
        override val contactsSource: Lazy<ContactsSource> = graph.contactsSource
    }
}