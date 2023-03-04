package com.example.effectivemobiletest

import android.app.Application
import com.example.effectivemobiletest.di.AppComponent
import com.example.effectivemobiletest.di.DaggerAppComponent
import com.example.effectivemobiletest.di.DatabaseModule

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .databaseModule(DatabaseModule(this))
            .build()
    }
}