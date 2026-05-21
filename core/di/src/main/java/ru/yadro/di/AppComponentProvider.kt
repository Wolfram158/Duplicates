package ru.yadro.di

import android.content.Context

object AppComponentProvider {
    fun provideAppComponent(context: Context): AppComponent {
        return object : AppComponent {
            override val context: Context = context
        }
    }
}