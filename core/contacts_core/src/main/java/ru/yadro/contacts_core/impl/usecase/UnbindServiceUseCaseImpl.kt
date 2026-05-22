package ru.yadro.contacts_core.impl.usecase

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import ru.yadro.contacts_core.api.DeleteDuplicatesRepository
import ru.yadro.contacts_core.api.usecase.UnbindServiceUseCase
import ru.yadro.contacts_core.di.ContactsCoreScope

@SingleIn(ContactsCoreScope::class)
@ContributesBinding(ContactsCoreScope::class)
@Inject
internal class UnbindServiceUseCaseImpl(
    private val repository: DeleteDuplicatesRepository
) : UnbindServiceUseCase {
    override operator fun invoke() = repository.unbindService()
}