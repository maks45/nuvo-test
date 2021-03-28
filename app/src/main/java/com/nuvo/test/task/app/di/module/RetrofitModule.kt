package com.nuvo.test.task.app.di.module

import com.google.gson.GsonBuilder
import com.nuvo.test.task.app.repository.network.CryptoCurrencyNetwork
import com.nuvo.test.task.app.util.CryptoCurrencyTypeAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton


@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun provideCryptoCurrencyNetwork(properties: Properties): CryptoCurrencyNetwork {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request().newBuilder()
                    .addHeader("X-CMC_PRO_API_KEY", properties.getProperty("apiKey"))
                    .addHeader("Accept", "application/json")
                    .build()
                return chain.proceed(request)
            }
        })
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapterFactory(
            CryptoCurrencyTypeAdapterFactory()
        )
        val gson = gsonBuilder.create()
        val retrofit = Retrofit.Builder().baseUrl(
            properties.getProperty("baseUrl"))
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(CryptoCurrencyNetwork::class.java)
    }
}