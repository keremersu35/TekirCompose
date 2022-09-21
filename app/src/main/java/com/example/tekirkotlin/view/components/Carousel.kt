package com.example.tekirkotlin.view.components

import android.os.Build
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.tekirkotlin.model.Image
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Carousel(images: List<Image>) {

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    HorizontalPager(count = images.size) { page ->

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(images[page].url)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.None,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}