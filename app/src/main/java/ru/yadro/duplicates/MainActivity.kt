package ru.yadro.duplicates

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import ru.yadro.common.getAppComponent
import ru.yadro.common.ui.LocalAppComponent
import ru.yadro.common.ui.theme.DuplicatesTheme
import ru.yadro.contacts_list.ui.ContactsListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DuplicatesTheme {
                RequestPermissions()
                CompositionLocalProvider(LocalAppComponent provides getAppComponent()) {
                    ContactsListScreen()
                }
            }
        }
    }
}

@Composable
fun RequestPermissions() {
    val context = LocalContext.current
    val permissions = arrayOf(
        Manifest.permission.READ_CONTACTS
    )
    val areAllGranted = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {
    }
    if (!areAllGranted) {
        LaunchedEffect(Unit) {
            launcher.launch(permissions)
        }
    }
}