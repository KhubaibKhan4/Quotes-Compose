package com.codespacepro.quotescomposeapp.base

import android.app.Application
import com.codespacepro.quotescomposeapp.viewmodels.MainViewModel

class BaseApplication : Application() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreate() {
        super.onCreate()

    }




}