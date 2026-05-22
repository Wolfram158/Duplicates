package ru.yadro.contacts_core.di

import android.content.Context
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import ru.yadro.contacts_core.api.ContactsSource
import ru.yadro.contacts_core.api.ReactiveContactsSource
import ru.yadro.contacts_core.api.usecase.BindServiceUseCase
import ru.yadro.contacts_core.api.usecase.DeleteDuplicatesUseCase
import ru.yadro.contacts_core.api.usecase.UnbindServiceUseCase

@DependencyGraph(ContactsCoreScope::class)
internal interface ContactsCoreGraph {
    val reactiveContactsSource: Lazy<ReactiveContactsSource>
    val contactsSource: Lazy<ContactsSource>
    val deleteDuplicatesUseCase: Lazy<DeleteDuplicatesUseCase>
    val bindServiceUseCase: Lazy<BindServiceUseCase>
    val unbindServiceUseCase: Lazy<UnbindServiceUseCase>

    @DependencyGraph.Factory
    interface Factory {
        fun create(
            @Provides context: Context
        ): ContactsCoreGraph
    }
}