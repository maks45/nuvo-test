package com.nuvo.test.task.app.di.component

import com.nuvo.test.task.app.di.module.*
import com.nuvo.test.task.app.ui.MainActivity
import dagger.Component
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [
    AppModule::class,
    RoomModule::class,
    RetrofitModule::class
    ])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}