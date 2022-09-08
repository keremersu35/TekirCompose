package com.example.tekirkotlin.view.cat_detail

import android.os.Build.VERSION.SDK_INT
import android.webkit.WebStorage
import android.widget.RatingBar
import android.widget.Space
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.tekirkotlin.utils.checkName
import com.example.tekirkotlin.utils.linkToWebpage
import com.example.tekirkotlin.view.cat_list.components.Lottie
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.mahmoudalim.compose_rating_bar.RatingBarView
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

    val scrollState = rememberScrollState()

    Surface(color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()) {
        Column() {

            Column(modifier = Modifier
                .verticalScroll(state = scrollState)
                .weight(1f)) {
                Box() {
                    Box(
                        modifier = Modifier
                            .height(340.dp)
                            .align(TopCenter)
                            .fillMaxWidth()
                    ) {

                        Box(
                            modifier = Modifier
                                .align(Center)
                                .padding(bottom = 25.dp)
                                .clip(RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp))
                        ) {
                            catPhotos.data?.let { Carousel(it) }
                        }
                        cat.data?.let {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(BottomCenter),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                FeatureBox("Origin", text = cat.data?.origin?.checkName())
                                Spacer(modifier = Modifier.width(15.dp))
                                FeatureBox("Life Span", text = cat.data?.lifeSpan)
                                Spacer(modifier = Modifier.width(15.dp))
                                FeatureBox("Weight", text = cat.data?.weight?.metric)
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = cat.data?.name ?: "",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(Icons.Filled.Favorite, contentDescription = "", tint = Color.Gray)
                }

                Text(
                    text = cat.data?.description ?: "",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
                )

                Rating(title = "Energy", number = cat.data?.energyLevel?.toInt())
                Rating(title = "Adaptability", number = cat.data?.adaptability?.toInt())
                Rating(title = "Dog Friendly", number = cat.data?.dogFriendly?.toInt())
                Rating(title = "Intelligence", number = cat.data?.intelligence?.toInt())

            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp), horizontalArrangement = Arrangement.SpaceEvenly)
            {
                LinkButton(title = "CFA", url = cat.data?.cfaUrl ?: "")
                LinkButton(title = "Vet Street", url = cat.data?.vetstreetUrl ?: "")
                LinkButton(title = "Wikipedia", url = cat.data?.wikipediaUrl ?: "")
            }

            Spacer(modifier = Modifier.height(5.dp))
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
            contentScale = ContentScale.None,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}

@Composable
fun FeatureBox(title: String, text: String?) {

    Column(modifier = Modifier
        .height(60.dp)
        .width(100.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(MaterialTheme.colors.onBackground)
        .border(
            width = 2.dp,
            color = Color.Black,
            shape = RoundedCornerShape(10.dp)
        ), 
        verticalArrangement = Arrangement.Center) {

        Text(
            text = title, modifier = Modifier.align(CenterHorizontally),
            fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
        )

        Text(
            text = text ?: "", modifier = Modifier.align(CenterHorizontally),
            fontSize = 14.sp, textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Rating(title: String, number: Int?) {
    if(number != null){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

            Text(text = title)

            RatingBarView(
                rating = remember {mutableStateOf(number)},
                isViewAnimated = true,
                starIcon = painterResource(id = R.drawable.ic_star),
                unRatedStarsColor = Color.LightGray,
                ratedStarsColor = MaterialTheme.colors.onBackground,
                starSize = 30.dp,
            )
        }
    }
    else Box(){

    }
}

@Composable
fun LinkButton(title: String, url: String) {
    val context = LocalContext.current

    Button(onClick = { linkToWebpage(url, context) }, modifier = Modifier
        .height(55.dp)
        .clip(RoundedCornerShape(5.dp))
        .width(120.dp)
        .border(1.dp, Color.Black, RoundedCornerShape(5.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onSurface)
    ) {
        
        Icon(painter = painterResource(id = R.drawable.paw_icon), tint = Color.White, contentDescription = "",
        modifier = Modifier.size(30.dp).padding(5.dp))
        
        Text(text = title, color = Color.White)
    }
}