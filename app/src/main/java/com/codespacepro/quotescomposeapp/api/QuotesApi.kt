package com.codespacepro.quotescomposeapp.api

import com.codespacepro.quotescomposeapp.models.QuotesItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface QuotesApi {

    @GET("quotes/random")
    suspend fun getQuotes(
        @Query("limit") limit: Int,
        @Query("maxLength") maxLength: Int,
        @Query("minLength") minLength: Int,
        @Query("tags") tags: String,

        ): Response<List<QuotesItem>>

}