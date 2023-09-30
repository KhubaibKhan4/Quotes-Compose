package com.codespacepro.quotescomposeapp.screen

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowLeft
import androidx.compose.material.icons.sharp.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.codespacepro.quotescomposeapp.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    author: String?,
    content: String?,
    dateAdded: String?
) {

    val context = LocalContext.current
    val view = LocalView.current
    val density = LocalDensity.current.density

    Scaffold(topBar = {
        TopAppBar(
            {
                Text(
                    text = "Quote Detail",
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_text)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFF3A44),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .width(311.dp),
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Sharp.KeyboardArrowLeft, contentDescription = "")
                }
            },
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        )
    }, content = {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) Color.Black else Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_quote),
                        contentDescription = "",
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "$content",
                        style = TextStyle(
                            fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 28.sp,
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Author name
                    Text(
                        text = "- $author", style = TextStyle(
                            fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Gray
                        ),
                        textAlign = TextAlign.Start
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = " $dateAdded", style = TextStyle(
                            fontSize = 12.sp, fontWeight = FontWeight.Normal, color = Color.Gray
                        ),
                        modifier = Modifier.align(Alignment.End)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_quote),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.End)
                    )
                    Row(
                        Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    ) {

                        IconButton(onClick = {

                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            intent.putExtra(Intent.EXTRA_TEXT, "$content \n -  $author")
                            context.startActivity(intent)

                        }) {
                            Icon(imageVector = Icons.Sharp.Share, contentDescription = "share")
                        }

                        IconButton(onClick = {


                            // Get display metrics to calculate scale
                            val displayMetrics = DisplayMetrics()
                            val windowManager =
                                context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                            windowManager.defaultDisplay.getMetrics(displayMetrics)

                            // Calculate the scale factor
                            val scale = displayMetrics.density / density

                            // Calculate the scaled width and height
                            val scaledWidth = (view.width * scale).toInt()
                            val scaledHeight = (view.height * scale).toInt()

                            // Create a Bitmap of the scaled size
                            val bitmap = Bitmap.createBitmap(
                                scaledWidth,
                                scaledHeight,
                                Bitmap.Config.ARGB_8888
                            )
                            val canvas = Canvas(bitmap)

                            // Scale and draw the view onto the canvas
                            canvas.scale(scale, scale)
                            view.draw(canvas)

                            // Save the bitmap as an image
                            val savedUri = saveBitmapToGallery(context, bitmap)
                            Toast.makeText(
                                context,
                                "Image Saved SuccessFully...",
                                Toast.LENGTH_SHORT
                            ).show()

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.download),
                                contentDescription = "download"
                            )
                        }


                    }

                }
            }
        }
    })

}

fun saveBitmapToGallery(context: android.content.Context, bitmap: Bitmap): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "quote_image_${System.currentTimeMillis()}.png")
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    try {
        uri?.let { imageUri ->
            val outputStream = resolver.openOutputStream(imageUri)
            outputStream?.use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(imageUri, contentValues, null, null)
            }

            return imageUri
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}
