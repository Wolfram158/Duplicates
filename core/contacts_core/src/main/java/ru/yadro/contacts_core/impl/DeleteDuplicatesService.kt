package ru.yadro.contacts_core.impl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.yadro.contacts_core.DeleteDuplicatesResult
import ru.yadro.contacts_core.IDeleteDuplicatesCallback
import ru.yadro.contacts_core.IDeleteDuplicatesInterface

internal class DeleteDuplicatesService : Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val binder = object : IDeleteDuplicatesInterface.Stub() {
        override fun deleteDuplicates(callback: IDeleteDuplicatesCallback) {
            serviceScope.launch {
                val result = try {
                    val contacts = applicationContext.contentResolver.getContacts()
                    val duplicates = contacts.getDuplicates()
                    if (duplicates.isEmpty()) {
                        DeleteDuplicatesResult.NO_DUPLICATES
                    }
                    applicationContext.contentResolver.deleteDuplicates(duplicates)
                    DeleteDuplicatesResult.DELETED
                } catch (_: Exception) {
                    DeleteDuplicatesResult.ERROR
                }
                callback.onResult(result)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}