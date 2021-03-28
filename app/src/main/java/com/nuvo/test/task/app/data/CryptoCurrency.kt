package com.nuvo.test.task.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto_currency")
data class CryptoCurrency(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "symbol") var symbol: String,
    @ColumnInfo(name = "usd_price") var usdPrice: Double,
    @ColumnInfo(name = "btc_price") var btcPrice: Double
)