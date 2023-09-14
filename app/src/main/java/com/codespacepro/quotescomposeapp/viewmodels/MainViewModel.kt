package com.codespacepro.quotescomposeapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codespacepro.quotescomposeapp.models.QuotesItem
import com.codespacepro.quotescomposeapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    val myResponse: MutableLiveData<Response<List<QuotesItem>>> = MutableLiveData()

    fun getQuotes(
        limit: Int,
        maxLength: Int,
        minLength: Int,
        tags: String,

    ) {
        viewModelScope.launch {
            val response = repository.getQuotes(
                limit = limit,
                maxLength = maxLength,
                minLength = minLength,
                tags = tags,
            )
            myResponse.value = response
        }
    }
}