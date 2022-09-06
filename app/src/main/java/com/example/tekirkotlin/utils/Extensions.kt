package com.example.tekirkotlin.utils

fun String.checkName(country: String): String{
    return when (country) {
        "United States" -> {
            "US"
        }
        "United Kingdom" -> {
            "UK"
        }
        "United Arab Emirates" -> {
            "UAE"
        }
        else -> country
    }
}