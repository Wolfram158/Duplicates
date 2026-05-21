package ru.yadro.common.ui

import androidx.compose.runtime.staticCompositionLocalOf
import ru.yadro.di.AppComponent

val LocalAppComponent = staticCompositionLocalOf<AppComponent> {
    error("AppComponent not provided")
}