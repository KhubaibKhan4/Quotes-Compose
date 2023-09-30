package com.codespacepro.quotescomposeapp.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.codespacepro.quotescomposeapp.R
import com.codespacepro.quotescomposeapp.models.QuotesItem
import com.codespacepro.quotescomposeapp.navigation.Screen
import com.codespacepro.quotescomposeapp.repository.Repository
import com.codespacepro.quotescomposeapp.viewmodels.MainViewModel
import java.net.SocketTimeoutException

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    var isLoading by remember {
        mutableStateOf(true)
    }
    val scope = rememberCoroutineScope()
    val repository = Repository()
    val mainViewModel = MainViewModel(repository = repository)
    val owner: LifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val quoteListState = remember { mutableStateOf(emptyList<QuotesItem>()) }


    try {

        mainViewModel.getQuotes(
            limit = 40,
            maxLength = 50,
            minLength = 20,
            tags = "technology | famous-quotes",
        )
        mainViewModel.myResponse.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                val quotes = response.body() ?: emptyList()
                quoteListState.value = quotes
                isLoading = false
            } else {
                isLoading = false
                Toast.makeText(context, response.code().toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })


    } catch (e: SocketTimeoutException) {
        isLoading = false
        Log.d("Main", "HomeScreen: ${e.localizedMessage}")
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Random Quotes") },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = if (isSystemInDarkTheme()) Color.Black else MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White
            ),
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        )

    }, content = {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            if (isLoading) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (quoteListState.value.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "OOPS!!! No Quotes  Found...",
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                QuotesList(quoteListState.value, navController)
            }
        }
    })

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuotesList(quotes: List<QuotesItem>, navController: NavHostController) {

    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {

        items(quotes) { quotes ->
            QuoteCard(quote = quotes, navController)
        }
    }

}

@Composable
fun QuoteCard(quote: QuotesItem, navController: NavHostController) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clickable(
                indication = null, interactionSource = MutableInteractionSource(),
                role = Role.Image
            ) {
                navController.navigate(
                    Screen.Detail.passData(
                        quote.author,
                        quote.content,
                        quote.dateAdded
                    )
                )
            },

        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) Color.Black else Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center
        ) {
            // Quote content
            Text(
                text = quote.content, style = TextStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 28.sp,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Author name
            Text(
                text = "- ${quote.author}", style = TextStyle(
                    fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_quote),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }

}


