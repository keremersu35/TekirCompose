package com.example.tekirkotlin.view.cat_detail

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.tekirkotlin.R
import com.example.tekirkotlin.model.CatDetail
import com.example.tekirkotlin.model.Image
import com.example.tekirkotlin.utils.Resource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun CatDetailScreen(id: String, navController: NavController,
    viewModel: CatDetailViewModel = hiltViewModel()) {

    val cat = produceState<Resource<CatDetail>>(initialValue = Resource.Loading()) {
        value = viewModel.getCatInfo(id)
    }.value

    val catPhotos = produceState<Resource<List<Image>>>(initialValue = Resource.Loading()) {
        value = viewModel.getCatPhotos(id)
    }.value


    Surface(color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.fillMaxSize()) {

            Box(modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter).height(300.dp)){
                catPhotos.data?.let { Carousel(it) }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Carousel(images: List<Image>) {

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
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
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(RoundedCornerShape(15.dp))
        )
    }


}