package com.example.tekirkotlin.view.cat_detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tekirkotlin.model.Cat
import com.example.tekirkotlin.model.CatDetail
import com.example.tekirkotlin.model.Image
import com.example.tekirkotlin.utils.FavListManager
import com.example.tekirkotlin.utils.Resource
import com.example.tekirkotlin.utils.checkName
import com.example.tekirkotlin.view.components.Carousel
import com.example.tekirkotlin.view.components.FeatureBox
import com.example.tekirkotlin.view.components.LinkButton
import com.example.tekirkotlin.view.components.Rating

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

    var color by remember{mutableStateOf(Color.Gray)}

    val context = LocalContext.current
    val favListManager = FavListManager(context = context)

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

                    Box(modifier = Modifier.clickable ( onClick = {
                        favListManager.addBreed(cat = Cat(id = cat.data!!.id, cat.data.name, cat.data.origin, cat.data.countryCode, cat.data.description,
                            catPhotos.data!![0], cat.data.lifeSpan))
                        color = if(favListManager.checkBreed(cat.data?.id ?: "")){
                            Color.Red
                        }else{
                            Color.Gray
                        }
                    })){
                        if(favListManager.checkBreed(cat.data?.id ?: "")){
                            color = Color.Red
                        }

                        Icon(Icons.Filled.Favorite, contentDescription = "", tint = color)
                    }
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






