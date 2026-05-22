package ru.yadro.contacts_core.api.usecase

import ru.yadro.contacts_core.api.DeleteDuplicatesResult

interface DeleteDuplicatesUseCase {
    suspend operator fun invoke(): DeleteDuplicatesResult
}