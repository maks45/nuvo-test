package com.nuvo.test.task.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.nuvo.test.task.app.R
import com.nuvo.test.task.app.viewmodel.DetailActivityViewModel

class DetailActivity : AppCompatActivity() {
    lateinit var detailActivityViewModel: DetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        detailActivityViewModel =
            ViewModelProviders.of(this).get(DetailActivityViewModel::class.java)

    }
}