package com.example.tekirkotlin.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tekirkotlin.R
import com.mahmoudalim.compose_rating_bar.RatingBarView

@Composable
fun Rating(title: String, number: Int?) {
    if(number != null){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

            Text(text = title)

            RatingBarView(
                rating = remember { mutableStateOf(number) },
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