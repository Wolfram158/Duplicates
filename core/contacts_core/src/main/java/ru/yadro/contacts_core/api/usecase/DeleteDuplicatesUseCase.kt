package ru.yadro.contacts_core.api.usecase

import ru.yadro.contacts_core.api.DeleteDuplicatesResult

interface DeleteDuplicatesUseCase {
    operator fun invoke(): DeleteDuplicatesResult
}