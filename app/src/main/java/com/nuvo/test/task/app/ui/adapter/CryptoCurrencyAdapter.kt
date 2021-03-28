package com.nuvo.test.task.app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nuvo.test.task.app.R
import com.nuvo.test.task.app.data.CryptoCurrency

class CryptoCurrencyAdapter(var context: Context, var cryptoCurrencyList: List<CryptoCurrency>) :
    RecyclerView.Adapter<CryptoCurrencyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.crypto_currency_item_view, parent, false)
        )
    }

    fun setCryptoCurrenciesList(cryptoCurrencyList: List<CryptoCurrency>) {
        this.cryptoCurrencyList = cryptoCurrencyList
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return cryptoCurrencyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewCurrencyCode.text = cryptoCurrencyList[position].symbol
        holder.textViewCurrencyName.text = cryptoCurrencyList[position].name
        holder.textViewCurrencyUsdExchangeRate.text =
            context.getString(
                R.string.usd_end,
                cryptoCurrencyList[position].usdPrice
            )
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewCurrencyCode: TextView = itemView.findViewById(R.id.textViewCurrencyCode)
        val textViewCurrencyName: TextView = itemView.findViewById(R.id.textViewCurrencyName)
        val textViewCurrencyUsdExchangeRate: TextView =
            itemView.findViewById(R.id.textViewUsdExchangeRate)
    }
}