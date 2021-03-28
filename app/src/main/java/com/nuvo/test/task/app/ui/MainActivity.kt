package com.nuvo.test.task.app.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.nuvo.test.task.app.App
import com.nuvo.test.task.app.R
import com.nuvo.test.task.app.repository.CryptoCurrencyRepository
import com.nuvo.test.task.app.ui.adapter.CryptoCurrencyAdapter
import com.nuvo.test.task.app.viewmodel.MainActivityViewModel
import com.nuvo.test.task.app.viewmodel.MainActivityViewModelFactory
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() , SearchView.OnQueryTextListener {
    private val layoutManager = LinearLayoutManager(this)

    @Inject
    lateinit var cryptoCurrencyRepository: CryptoCurrencyRepository
    lateinit var cryptoCurrencyAdapter: CryptoCurrencyAdapter
    lateinit var mainCryptoCurrenciesRecyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var snackBar: Snackbar
    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        setContentView(R.layout.activity_main)
        mainActivityViewModel = ViewModelProviders.of(this, MainActivityViewModelFactory(
            application as App, cryptoCurrencyRepository
        )).get(MainActivityViewModel::class.java)
        cryptoCurrencyAdapter = CryptoCurrencyAdapter(this, Collections.emptyList())
        mainCryptoCurrenciesRecyclerView = findViewById(R.id.recyclerView)
        mainCryptoCurrenciesRecyclerView.adapter = cryptoCurrencyAdapter
        mainCryptoCurrenciesRecyclerView.layoutManager = layoutManager
        mainActivityViewModel.cryptoCurrenciesLiveData.observe(this, Observer {
            cryptoCurrencyAdapter.setCryptoCurrenciesList(it)
        })
        snackBar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_INDEFINITE)
        mainActivityViewModel.messageLiveData.observe(this, Observer {
            Log.d(TAG, "onCreate: ${it.isBlank()}")
            if (it.isNotBlank()) {
                snackBar.setText(it)
                snackBar.show()
            }
            if (it.isBlank()) {
                snackBar.dismiss()
            }
        })
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            mainActivityViewModel.updateCryptoCurrenciesList()
        }
        mainActivityViewModel.isLoadingLiveData.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it
        })
    }

    override fun onDestroy() {
        if(isFinishing){
            mainActivityViewModel.saveLastUpdateTime()
        }
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.getActionView() as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //mainActivityViewModel.searchItem(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        mainActivityViewModel.searchItem(newText)
        return false
    }
}