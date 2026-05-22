package ru.yadro.contacts_core.di

import dev.zacsweers.metro.createGraphFactory
import ru.yadro.contacts_core.api.ContactsSource
import ru.yadro.contacts_core.api.ReactiveContactsSource
import ru.yadro.contacts_core.api.usecase.BindServiceUseCase
import ru.yadro.contacts_core.api.usecase.DeleteDuplicatesUseCase
import ru.yadro.contacts_core.api.usecase.UnbindServiceUseCase
import ru.yadro.di.AppComponent

interface ContactsCoreComponent {
    val reactiveContactsSource: Lazy<ReactiveContactsSource>
    val contactsSource: Lazy<ContactsSource>
    val deleteDuplicatesUseCase: Lazy<DeleteDuplicatesUseCase>
    val bindServiceUseCase: Lazy<BindServiceUseCase>
    val unbindServiceUseCase: Lazy<UnbindServiceUseCase>
}

fun AppComponent.createContactsCoreComponent(): ContactsCoreComponent {
    val graph = createGraphFactory<ContactsCoreGraph.Factory>().create(context)
    return object : ContactsCoreComponent {
        override val reactiveContactsSource: Lazy<ReactiveContactsSource> =
            graph.reactiveContactsSource
        override val contactsSource: Lazy<ContactsSource> = graph.contactsSource
        override val deleteDuplicatesUseCase: Lazy<DeleteDuplicatesUseCase> =
            graph.deleteDuplicatesUseCase
        override val bindServiceUseCase: Lazy<BindServiceUseCase> = graph.bindServiceUseCase
        override val unbindServiceUseCase: Lazy<UnbindServiceUseCase> = graph.unbindServiceUseCase
    }
}