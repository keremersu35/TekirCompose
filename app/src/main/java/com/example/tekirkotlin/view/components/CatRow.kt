package com.example.tekirkotlin.view.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tekirkotlin.R
import com.example.tekirkotlin.model.Cat
import com.example.tekirkotlin.utils.FavListManager
import com.example.tekirkotlin.utils.checkName

@Composable
fun CatRow(navController: NavController, cat: Cat) {

    val context: Context = LocalContext.current
    var favListManager = FavListManager(context)
    var color by remember{mutableStateOf(Color.Gray)}

    Box() {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.TopEnd)
                .zIndex(1f)
                .clip(CircleShape)
                .background(Color.White)
                .clickable(onClick =

                    { favListManager.addBreed(breedId = cat.id)
                        color = if(favListManager.checkBreed(cat.id)){
                            Color.Red
                        }else{
                            Color.Gray
                        }
                    })
        ) {

            if(favListManager.checkBreed(cat.id)){
                color = Color.Red
            }

            Icon(
                Icons.Filled.Favorite, "",
                modifier = Modifier
                    .padding(5.dp),
                tint = color
            )
        }
        Column(modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(15.dp))
            .height(250.dp)
            .background(color = Color.White)
            .clickable {
                navController.navigate(
                    "cat_detail_screen/${cat.id}"
                )
            }) {

            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                if (cat.image?.url == null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.cat)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.loading),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(RoundedCornerShape(15.dp))
                    )
                } else {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(cat.image?.url)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.loading),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(RoundedCornerShape(15.dp))
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {

                Text(
                    text = cat.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally)
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.marker),
                        contentDescription = "",
                        modifier = Modifier
                            .width(15.dp)
                            .height(15.dp),
                        tint = Color.Gray
                    )

                    Text(
                        text = cat.origin.checkName(),
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.width(15.dp))


                    Box(){
                        Icon(
                            painter = painterResource(id = R.drawable.life_span),
                            contentDescription = "",
                            modifier = Modifier
                                .width(15.dp)
                                .height(15.dp),
                            tint = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = cat.lifeSpan.toString(),
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}