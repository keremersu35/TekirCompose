package com.example.tekirkotlin.service

import com.example.tekirkotlin.model.Cat
import com.example.tekirkotlin.model.CatDetail
import com.example.tekirkotlin.model.Image
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("breeds")
    suspend fun getCats(): Call<List<Cat>>

    @GET("breeds/{breedId}")
    suspend fun getCat(@Path("breedId") breedId: String): Call<CatDetail>

    @GET("images/search/")
    suspend fun getCatPhotos(@Query("breed_ids") breedId: String, @Query("limit") limit: Long, @Query("api_key") apiKey: String): Call<List<Image>>
}