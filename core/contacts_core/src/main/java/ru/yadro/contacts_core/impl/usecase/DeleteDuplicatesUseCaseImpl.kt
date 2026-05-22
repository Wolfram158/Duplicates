package ru.yadro.contacts_core.impl.usecase

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import ru.yadro.contacts_core.api.DeleteDuplicatesRepository
import ru.yadro.contacts_core.api.DeleteDuplicatesResult
import ru.yadro.contacts_core.api.usecase.DeleteDuplicatesUseCase
import ru.yadro.contacts_core.di.ContactsCoreScope

@SingleIn(ContactsCoreScope::class)
@ContributesBinding(ContactsCoreScope::class)
@Inject
internal class DeleteDuplicatesUseCaseImpl(
    private val service: DeleteDuplicatesRepository
) : DeleteDuplicatesUseCase {
    override suspend operator fun invoke(): DeleteDuplicatesResult = service.deleteDuplicates()
}