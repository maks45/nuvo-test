package com.nuvo.test.task.app.repository.network

import com.nuvo.test.task.app.data.CryptoCurrency
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoCurrencyNetwork {
    @GET("v1/cryptocurrency/listings/latest?")
    suspend fun getCryptoCurrenciesList(
        @Query("convert") convert: String
    ): List<CryptoCurrency>
}