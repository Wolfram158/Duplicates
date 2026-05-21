package ru.yadro.contacts_list.domain.api.usecase

import kotlinx.coroutines.flow.Flow
import ru.yadro.contacts_list.domain.api.model.ContactOrLetter

internal interface GetContactsFlowUseCase {
    operator fun invoke(): Flow<List<ContactOrLetter>>
}