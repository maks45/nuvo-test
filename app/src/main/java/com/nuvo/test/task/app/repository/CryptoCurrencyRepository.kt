package com.nuvo.test.task.app.repository

import com.nuvo.test.task.app.data.CryptoCurrency
import com.nuvo.test.task.app.repository.db.CryptoCurrencyDb
import com.nuvo.test.task.app.repository.network.CryptoCurrencyNetwork
import java.util.*

class CryptoCurrencyRepository constructor(
    val cryptoCurrencyNetwork: CryptoCurrencyNetwork,
    val cryptoCurrencyDb: CryptoCurrencyDb,
    val properties: Properties
) {
    suspend fun getCryptoCurrenciesList(): List<CryptoCurrency>{
        return cryptoCurrencyNetwork.getCryptoCurrenciesList(properties.getProperty("convert"))
    }

    suspend fun getCachedCryptoCurrencies(): List<CryptoCurrency>{
        return cryptoCurrencyDb.cryptoCurrencyDao().getAll()
    }

    suspend fun updateCachedCryptoCurrencies(cryptoCurrencies: List<CryptoCurrency>){
        cryptoCurrencyDb.cryptoCurrencyDao().addAll(cryptoCurrencies)
    }
}