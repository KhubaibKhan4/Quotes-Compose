package com.codespacepro.quotescomposeapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codespacepro.quotescomposeapp.models.QuotesItem
import com.codespacepro.quotescomposeapp.network.NetworkUtils
import com.codespacepro.quotescomposeapp.repository.Repository
import com.codespacepro.quotescomposeapp.screen.QuotesList
import com.codespacepro.quotescomposeapp.ui.theme.QuotesComposeAppTheme
import com.codespacepro.quotescomposeapp.viewmodels.MainViewModel
import com.codespacepro.quotescomposeapp.viewmodels.MainViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val repository = Repository()
            val mainViewModelFactory = MainViewModelFactory(repository)
            var isLoading by remember {
                mutableStateOf(true)
            }

            mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

            val quoteListState = remember { mutableStateOf(emptyList<QuotesItem>()) }
            mainViewModel.getQuotes(
                limit = 40,
                maxLength = 50,
                minLength = 20,
                tags = "technology | famous-quotes",
            )

            if (NetworkUtils.isInternetAvailable(applicationContext)) {
                mainViewModel.myResponse.observe(this, Observer { response ->
                    if (response.isSuccessful) {
                        isLoading = false
                        val quotes = response.body() ?: emptyList()
                        quoteListState.value = quotes
                        repeat(30) {
                            Log.d("response", response.body()?.get(it)?.content.toString())
                            Log.d("response", response.body()?.get(it)?.author.toString())
                            Log.d("response", response.body()?.get(it)?.authorSlug.toString())
                            Log.d("response", response.body()?.get(it)?._id.toString())
                        }

                    } else {
                        isLoading = false
                        Toast.makeText(context, response.code().toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            } else {
                isLoading = true
            }

            QuotesComposeAppTheme {

                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text(text = "Random Quotes") },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = if (isSystemInDarkTheme()) Color.Black else MaterialTheme.colorScheme.primary,
                            titleContentColor = Color.White
                        ),
                    )
                }, content = {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = it.calculateTopPadding()),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        QuotesList(quoteListState.value, isLoading)
                    }
                })

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuotesComposeAppTheme {
    }
}