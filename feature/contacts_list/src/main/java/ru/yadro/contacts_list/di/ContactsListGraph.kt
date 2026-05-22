package ru.yadro.contacts_list.di

import android.content.Context
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.createGraphFactory
import ru.yadro.contacts_core.api.ReactiveContactsSource
import ru.yadro.contacts_core.api.usecase.BindServiceUseCase
import ru.yadro.contacts_core.api.usecase.DeleteDuplicatesUseCase
import ru.yadro.contacts_core.api.usecase.UnbindServiceUseCase
import ru.yadro.contacts_core.di.createContactsCoreComponent
import ru.yadro.contacts_list.ui.ContactsListViewModelFactory
import ru.yadro.di.AppComponent

@DependencyGraph(ContactsListScope::class)
internal interface ContactsListGraph {
    val contactsListViewModelFactory: ContactsListViewModelFactory

    @DependencyGraph.Factory
    interface Factory {
        fun create(
            @Provides context: Context,
            @Provides reactiveContactsSource: ReactiveContactsSource,
            @Provides deleteDuplicatesUseCase: DeleteDuplicatesUseCase,
            @Provides bindServiceUseCase: BindServiceUseCase,
            @Provides unbindServiceUseCase: UnbindServiceUseCase
        ): ContactsListGraph
    }
}

internal fun AppComponent.createContactsListGraph(): ContactsListGraph {
    val contactsCoreComponent = createContactsCoreComponent()
    return createGraphFactory<ContactsListGraph.Factory>()
        .create(
            context,
            contactsCoreComponent.reactiveContactsSource.value,
            contactsCoreComponent.deleteDuplicatesUseCase.value,
            contactsCoreComponent.bindServiceUseCase.value,
            contactsCoreComponent.unbindServiceUseCase.value
        )
}