package ru.yadro.common

import android.app.Application
import android.content.Context
import ru.yadro.di.AppComponentProvider

class App : Application() {
    val appComponent by lazy(LazyThreadSafetyMode.NONE) {
        AppComponentProvider.provideAppComponent(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent
    }
}

fun Context.getAppComponent() = (applicationContext as App).appComponent