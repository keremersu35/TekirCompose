package com.example.tekirkotlin.model

import com.google.gson.annotations.SerializedName

data class Cat(
    val id: String,
    val name: String,
    val origin: String,
    @SerializedName("country_code")
    val countryCode: String,
    val description: String,
    val image: Image?,
    @SerializedName("life_span")
    val lifeSpan: String?,
)
