package com.nuvo.test.task.app.di.module

import com.nuvo.test.task.app.App
import com.nuvo.test.task.app.repository.CryptoCurrencyRepository
import com.nuvo.test.task.app.repository.db.CryptoCurrencyDb
import com.nuvo.test.task.app.repository.network.CryptoCurrencyNetwork
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class AppModule(var app: App) {
    @Provides
    @Singleton
    fun provideApplication(): App {
        return app
    }

    @Provides
    @Singleton
    fun provideProperties(app: App): Properties {
        val properties = Properties()
        val assetManager = app.assets
        val inputStream = assetManager.open("config.properties")
        properties.load(inputStream)
        return properties
    }

    @Provides
    @Singleton
    fun provideRepository(
        cryptoCurrencyNetwork: CryptoCurrencyNetwork,
        cryptoCurrencyDb: CryptoCurrencyDb,
        properties: Properties
    ): CryptoCurrencyRepository {
        return CryptoCurrencyRepository(cryptoCurrencyNetwork, cryptoCurrencyDb, properties)
    }

}