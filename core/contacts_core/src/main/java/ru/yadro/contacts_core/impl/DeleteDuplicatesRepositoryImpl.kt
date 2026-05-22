package ru.yadro.contacts_core.impl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import ru.yadro.contacts_core.IDeleteDuplicatesInterface
import ru.yadro.contacts_core.api.DeleteDuplicatesRepository
import ru.yadro.contacts_core.api.DeleteDuplicatesResult
import ru.yadro.contacts_core.di.ContactsCoreScope
import ru.yadro.contacts_core.DeleteDuplicatesResult as DeleteDuplicatesResultAidl

@SingleIn(ContactsCoreScope::class)
@ContributesBinding(ContactsCoreScope::class)
@Inject
internal class DeleteDuplicatesRepositoryImpl(
    private val context: Context
) : DeleteDuplicatesRepository {
    private var service: IDeleteDuplicatesInterface? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName,
            service: IBinder
        ) {
            this@DeleteDuplicatesRepositoryImpl.service =
                IDeleteDuplicatesInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            service = null
        }

    }

    override fun deleteDuplicates(): DeleteDuplicatesResult {
        return try {
            service?.run {
                when (deleteDuplicates()) {
                    DeleteDuplicatesResultAidl.NO_DUPLICATES -> DeleteDuplicatesResult.NoDuplicates
                    DeleteDuplicatesResultAidl.DELETED -> DeleteDuplicatesResult.Deleted
                    DeleteDuplicatesResultAidl.ERROR -> DeleteDuplicatesResult.Error
                    else -> throw RuntimeException("Invariant violation: only three types can be returned by service")
                }
            } ?: DeleteDuplicatesResult.Error
        } catch (_: Exception) {
            DeleteDuplicatesResult.Error
        }
    }

    override fun bindService() {
        Intent(context, DeleteDuplicatesService::class.java).also { intent ->
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun unbindService() {
        context.unbindService(connection)
    }
}