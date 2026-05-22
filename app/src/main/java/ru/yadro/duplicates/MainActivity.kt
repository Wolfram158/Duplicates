package ru.yadro.duplicates

import android.content.Context
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import ru.yadro.common.getAppComponent
import ru.yadro.common.ui.LocalAppComponent
import ru.yadro.common.ui.theme.DuplicatesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DuplicatesTheme {
                val context = LocalContext.current
                val areAllGranted =
                    remember { mutableStateOf(areAllGranted(context, Constants.permissions)) }
                if (!areAllGranted.value) {
                    RequestPermissions(
                        context = context,
                        permissions = Constants.permissions,
                        onGrantAllPermissions = { areAllGranted.value = true }
                    )
                } else {
                    CompositionLocalProvider(LocalAppComponent provides getAppComponent()) {
                        val navHostController = rememberNavController()
                        NavGraph(navHostController)
                    }
                }
            }
        }
    }
}

fun areAllGranted(
    context: Context,
    permissions: Array<String>
): Boolean {
    return permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
}

@Composable
fun RequestPermissions(
    context: Context,
    permissions: Array<String>,
    onGrantAllPermissions: () -> Unit
) {
    val areAllGranted = areAllGranted(context, permissions)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (areAllGranted(context, permissions)) {
            onGrantAllPermissions()
        }
    }
    LaunchedEffect(Unit) {
        if (!areAllGranted) {
            launcher.launch(permissions)
        }
    }
}