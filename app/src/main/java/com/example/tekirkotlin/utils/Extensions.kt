package com.example.tekirkotlin.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity

fun String.checkName(): String{
    return when (this) {
        "United States" -> {
            "US"
        }
        "United Kingdom" -> {
            "UK"
        }
        "United Arab Emirates" -> {
            "UAE"
        }
        else -> this
    }
}


fun linkToWebpage(url: String, context: Context) {
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse(url)
    startActivity(context, openURL, null)
}