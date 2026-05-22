package ru.yadro.contacts_core.impl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.yadro.contacts_core.DeleteDuplicatesResult
import ru.yadro.contacts_core.IDeleteDuplicatesInterface

internal class DeleteDuplicatesService : Service() {
    private val binder = object : IDeleteDuplicatesInterface.Stub() {
        override fun deleteDuplicates(): Byte {
            try {
                val contacts = applicationContext.contentResolver.getContacts()
                val duplicates = contacts.getDuplicates()
                if (duplicates.isEmpty()) {
                    return DeleteDuplicatesResult.NO_DUPLICATES
                }
                applicationContext.contentResolver.deleteDuplicates(duplicates)
                return DeleteDuplicatesResult.DELETED
            } catch (_: Exception) {
                return DeleteDuplicatesResult.ERROR
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}