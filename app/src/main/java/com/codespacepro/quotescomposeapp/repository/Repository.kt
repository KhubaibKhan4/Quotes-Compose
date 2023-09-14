package com.codespacepro.quotescomposeapp.repository

import com.codespacepro.quotescomposeapp.api.RetrofitInstance
import com.codespacepro.quotescomposeapp.models.QuotesItem
import retrofit2.Response

class Repository {
    suspend fun getQuotes(
        limit: Int,
        maxLength: Int,
        minLength: Int,
        tags: String,
    ): Response<List<QuotesItem>> {

        return RetrofitInstance.api.getQuotes(limit, maxLength, minLength, tags)
    }


}