package com.nuvo.test.task.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nuvo.test.task.app.App
import com.nuvo.test.task.app.repository.CryptoCurrencyRepository


class MainActivityViewModelFactory(val app: App, val repository: CryptoCurrencyRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(app, repository) as T
    }
}