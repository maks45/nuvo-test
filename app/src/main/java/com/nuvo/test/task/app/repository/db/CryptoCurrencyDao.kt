package com.nuvo.test.task.app.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nuvo.test.task.app.data.CryptoCurrency

@Dao
interface CryptoCurrencyDao {
    @Query("select * from crypto_currency;")
    suspend fun getAll(): List<CryptoCurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(cryptoCurrencies: List<CryptoCurrency>)
}