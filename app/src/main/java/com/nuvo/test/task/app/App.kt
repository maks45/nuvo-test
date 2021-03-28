package com.nuvo.test.task.app

import android.app.Application
import com.nuvo.test.task.app.di.component.AppComponent
import com.nuvo.test.task.app.di.component.DaggerAppComponent
import com.nuvo.test.task.app.di.module.AppModule
import com.nuvo.test.task.app.di.module.RetrofitModule
import com.nuvo.test.task.app.di.module.RoomModule

class App() : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerAppComponent.builder().appModule(AppModule(this))
                .retrofitModule(RetrofitModule()).roomModule(RoomModule(this))
                .build()
    }
}