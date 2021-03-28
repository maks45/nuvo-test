package com.nuvo.test.task.app.repository.db

import androidx.room.Database
import androidx.room.Query
import androidx.room.RoomDatabase
import com.nuvo.test.task.app.data.CryptoCurrency

@Database(entities = [CryptoCurrency::class], version = 1)
abstract class CryptoCurrencyDb() : RoomDatabase() {
    abstract fun cryptoCurrencyDao(): CryptoCurrencyDao
}