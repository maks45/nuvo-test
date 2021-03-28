package com.nuvo.test.task.app.di.module

import androidx.room.Room
import com.nuvo.test.task.app.App
import com.nuvo.test.task.app.repository.db.CryptoCurrencyDb
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class RoomModule(val app: App) {
    @Provides
    @Singleton
    fun provideDatabase(app: App, properties: Properties): CryptoCurrencyDb {
        return Room.databaseBuilder(
            app.applicationContext,
            CryptoCurrencyDb::class.java, properties.getProperty("databaseName")
        ).build()
    }
}