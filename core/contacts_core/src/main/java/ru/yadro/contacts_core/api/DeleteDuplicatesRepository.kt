package ru.yadro.contacts_core.api

sealed interface DeleteDuplicatesResult {
    object Error : DeleteDuplicatesResult
    object Deleted : DeleteDuplicatesResult
    object NoDuplicates : DeleteDuplicatesResult
}

interface DeleteDuplicatesRepository {
    suspend fun deleteDuplicates(): DeleteDuplicatesResult

    fun bindService()

    fun unbindService()
}