package com.codespacepro.quotescomposeapp.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codespacepro.quotescomposeapp.R
import com.codespacepro.quotescomposeapp.models.QuotesItem
import com.codespacepro.quotescomposeapp.util.AlertDialogExample

@Composable
fun HomeScreen() {


}

@Composable
fun QuoteCard(quote: QuotesItem, onClick: () -> Unit, visibility: Boolean) {
    var visibility by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clickable {
                visibility = !visibility
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

            if (visibility) {
                AlertDialogExample(
                    onDismissRequest = { visibility = false },
                    onConfirmation = { visibility },
                    dialogTitle = quote.author,
                    dialogText = quote.content,
                )
            }
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuotesList(quotes: List<QuotesItem>, isLoading: Boolean) {
    var content by remember {
        mutableStateOf<String?>(null)
    }
    var author by remember {
        mutableStateOf<String?>(null)
    }


    var visibility by remember {
        mutableStateOf<Boolean>(false)
    }

    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {

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
        } else {
           items(quotes){quotes ->
               QuoteCard(quote = quotes, onClick = {
                   content = quotes.content
                   author = quotes.author
               }, visibility = visibility)
           }

        }
    }

}