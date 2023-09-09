package com.codespacepro.quotescomposeapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codespacepro.quotescomposeapp.R
import com.codespacepro.quotescomposeapp.data.QuotesItem

@Composable
fun HomeScreen() {



}

@Composable
fun QuoteCard(quote: QuotesItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)),

        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center
        ) {
            // Quote content
            Text(
                text = quote.content, style = TextStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 28.sp
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

@Composable
fun QuotesList(quotes: List<QuotesItem>, isLoading: Boolean) {

    LazyColumn {
        items(quotes) { quote ->
            QuoteCard(quote = quote)
        }
        // Display a loading indicator while isLoading is true
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(48.dp), // Adjust the height as needed
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}