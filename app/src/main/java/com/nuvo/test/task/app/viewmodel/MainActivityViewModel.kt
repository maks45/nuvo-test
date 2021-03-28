package com.nuvo.test.task.app.viewmodel

import android.content.Context.MODE_PRIVATE
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nuvo.test.task.app.App
import com.nuvo.test.task.app.R
import com.nuvo.test.task.app.data.CryptoCurrency
import com.nuvo.test.task.app.repository.CryptoCurrencyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainActivityViewModel @Inject constructor(
    application: App,
    var cryptoCurrencyRepository: CryptoCurrencyRepository
) : AndroidViewModel(application) {
    companion object {
        const val SHARED_PREFERENCES_NAME = "SP_NAME"
        const val LAST_UPDATE_TIME_NAME = "LAST_UPDATE_TIME"
        const val CHECK_TIME_INTERVAL = 1L
        const val UPDATE_INTERVAL = 300_000L
        const val UPDATE_INTERVAL_MINUTES = 5L
    }

    private var cryptoCurrenciesList: List<CryptoCurrency> = ArrayList()
    var lastUpdate: Long
    var updateTimer: CountDownTimer
    val cryptoCurrenciesLiveData = MutableLiveData<List<CryptoCurrency>>(cryptoCurrenciesList)
    val messageLiveData = MutableLiveData<String>("")
    val isLoadingLiveData = MutableLiveData<Boolean>(false)

    init {
        lastUpdate =
            getApplication<App>().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
                .getLong(
                    LAST_UPDATE_TIME_NAME, Calendar.getInstance().time.time
                )
        updateTimer = object : CountDownTimer(UPDATE_INTERVAL, CHECK_TIME_INTERVAL) {
            override fun onFinish() {
                updateCryptoCurrenciesList()
            }

            override fun onTick(millisUntilFinished: Long) {
                checkTime()
            }
        }
        updateCryptoCurrenciesList()
        updateTimer.start()
    }

    fun updateCryptoCurrenciesList() {
        updateTimer.start()
        messageLiveData.postValue("")
        isLoadingLiveData.postValue(true)
        CoroutineScope(IO).launch {
            try {
                cryptoCurrenciesList = cryptoCurrencyRepository.getCryptoCurrenciesList()
                cryptoCurrencyRepository.updateCachedCryptoCurrencies(cryptoCurrenciesList)
                cryptoCurrenciesLiveData.postValue(cryptoCurrenciesList)
                setTime()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (cryptoCurrenciesList.isEmpty()) {
                cryptoCurrenciesList = cryptoCurrencyRepository.getCachedCryptoCurrencies()
                cryptoCurrenciesLiveData.postValue(cryptoCurrenciesList)
            }
        }.invokeOnCompletion {
            isLoadingLiveData.postValue(false)
            checkTime()
        }
    }

    private fun setTime() {
        lastUpdate = Calendar.getInstance().time.time
    }

    private fun getLastUpdateTime(): String {
        val currentTime = Calendar.getInstance().time
        val hours = (currentTime.time - lastUpdate) / (1000 * 3600)
        val minutes = (currentTime.time - lastUpdate) / (1000 * 60)
        val stringBuilder = StringBuilder()
        stringBuilder.append("last update was")
        if (hours > 0) stringBuilder.append(" $hours hour${if (hours == 0L) "" else "s"}")
        if (minutes > 0) stringBuilder.append(" $minutes minute${if (minutes == 0L) "" else "s"}")
        stringBuilder.append(" ago")
        return stringBuilder.toString()
    }

    fun checkTime() {
        if ((Calendar.getInstance().time.time - lastUpdate) / (1000 * 60) >= CHECK_TIME_INTERVAL) {
            messageLiveData.postValue(getLastUpdateTime())
        } else {
            messageLiveData.postValue("")
        }
    }

    fun saveLastUpdateTime() {
        getApplication<App>().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE).edit()
            .putLong(
                LAST_UPDATE_TIME_NAME, lastUpdate
            ).apply()
    }

    fun searchItem(query: String?) {
        query?.let {
            cryptoCurrenciesLiveData.postValue(cryptoCurrenciesList
                .filter { cryptoCurrency ->
                    cryptoCurrency.name.toLowerCase(Locale.ROOT)
                        .contains(it.toLowerCase(Locale.ROOT))
                            || cryptoCurrency.symbol.toLowerCase(Locale.ROOT)
                        .contains(it.toLowerCase(Locale.ROOT))
                })
        }
    }

}