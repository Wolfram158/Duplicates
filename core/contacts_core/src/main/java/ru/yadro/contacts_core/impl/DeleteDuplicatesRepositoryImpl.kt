package ru.yadro.contacts_core.impl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.yadro.contacts_core.IDeleteDuplicatesCallback
import ru.yadro.contacts_core.IDeleteDuplicatesInterface
import ru.yadro.contacts_core.api.DeleteDuplicatesRepository
import ru.yadro.contacts_core.api.DeleteDuplicatesResult
import ru.yadro.contacts_core.di.ContactsCoreScope
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
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

    override suspend fun deleteDuplicates(): DeleteDuplicatesResult {
        return suspendCancellableCoroutine { cont ->
            runCatching {
                service?.run {
                    deleteDuplicates(object : IDeleteDuplicatesCallback.Stub() {
                        override fun onResult(result: Byte) {
                            cont.resume(
                                when (result) {
                                    DeleteDuplicatesResultAidl.NO_DUPLICATES -> DeleteDuplicatesResult.NoDuplicates
                                    DeleteDuplicatesResultAidl.DELETED -> DeleteDuplicatesResult.Deleted
                                    DeleteDuplicatesResultAidl.ERROR -> DeleteDuplicatesResult.Error
                                    else -> throw RuntimeException("Invariant violation: only three types can be returned by service")
                                }
                            )
                        }
                    })
                } ?: cont.resume(DeleteDuplicatesResult.Error)
            }.onFailure {
                cont.resumeWithException(it)
            }
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