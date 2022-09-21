package com.example.tekirkotlin.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tekirkotlin.R
import com.example.tekirkotlin.utils.linkToWebpage


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