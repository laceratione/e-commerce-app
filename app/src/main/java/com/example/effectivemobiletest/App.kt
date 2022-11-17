package com.example.effectivemobiletest

import android.app.Application
import com.example.effectivemobiletest.di.AppComponent
import com.example.effectivemobiletest.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}