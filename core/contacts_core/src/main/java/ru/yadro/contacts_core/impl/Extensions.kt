package ru.yadro.contacts_core.impl

import android.content.ContentResolver
import android.database.ContentObserver
import android.net.Uri
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

internal fun ContentResolver.register(uri: Uri) = callbackFlow {
    val observer = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {
            trySend(selfChange)
        }
    }

    registerContentObserver(uri, false, observer)

    awaitClose {
        unregisterContentObserver(observer)
    }
}