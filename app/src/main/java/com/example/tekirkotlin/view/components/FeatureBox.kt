package com.example.tekirkotlin.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            text = title, modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
        )

        Text(
            text = text ?: "", modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 14.sp, textAlign = TextAlign.Center
        )
    }
}