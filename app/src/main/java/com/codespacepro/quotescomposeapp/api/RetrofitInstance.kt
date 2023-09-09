package com.codespacepro.quotescomposeapp.api

import com.codespacepro.quotescomposeapp.util.Constant.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: QuotesApi by lazy {
        retrofit.create(QuotesApi::class.java)
    }

}