package com.example.tekirkotlin.view.cat_list.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.tekirkotlin.R

@Composable
fun Lottie() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    var isLottiePlaying by remember {
        mutableStateOf(true)
    }
    LottieAnimation(
        composition = composition,
        isPlaying = isLottiePlaying,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxSize()
    )
}