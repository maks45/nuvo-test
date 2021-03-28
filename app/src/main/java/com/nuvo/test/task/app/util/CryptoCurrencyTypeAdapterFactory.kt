package com.nuvo.test.task.app.util

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.nuvo.test.task.app.data.CryptoCurrency
import org.json.JSONObject
import java.io.IOException
import java.lang.UnsupportedOperationException

class CryptoCurrencyTypeAdapterFactory : TypeAdapterFactory {

    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T> {
        val elementAdapter = gson?.getAdapter(JsonElement::class.java)
        return object : TypeAdapter<T>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter?, value: T) {
                throw UnsupportedOperationException()
            }
            @Throws(IOException::class)
            override fun read(it: JsonReader?): T {
                val cryptoCurrencyList = ArrayList<CryptoCurrency>()
                val jsonElement = elementAdapter!!.read(it)
                if (jsonElement.isJsonObject) {
                    val mainElement = jsonElement.asJsonObject
                    for (jsonEntry in mainElement.getAsJsonArray("data")) {
                        val currencyJsonObject = JSONObject(jsonEntry.toString())
                        val cryptoCurrency = CryptoCurrency(
                            currencyJsonObject.getInt("id"),
                            currencyJsonObject.getString("name"),
                            currencyJsonObject.getString("symbol"),
                            currencyJsonObject.getJSONObject("quote")
                                .getJSONObject("USD")
                                .getDouble("price"),
                            0.0
                        )
                        cryptoCurrencyList.add(cryptoCurrency)
                    }
                }
                return cryptoCurrencyList as T
            }
        }.nullSafe()
    }
}
