package com.example.tekirkotlin.repository

import com.example.tekirkotlin.model.Cat
import com.example.tekirkotlin.model.CatDetail
import com.example.tekirkotlin.model.Image
import com.example.tekirkotlin.service.ApiInterface
import com.example.tekirkotlin.utils.Resource
import retrofit2.Call
import java.lang.Exception
import javax.inject.Inject

class CatRepository @Inject constructor(
    private val api: ApiInterface
) {
    suspend fun getCats(): Resource<Call<List<Cat>>>{
        val response = try {
            api.getCats()
        } catch (e: Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

    suspend fun getCat(id: String): Resource<Call<CatDetail>>{
        val response = try {
            api.getCat(id)
        } catch (e: Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

    suspend fun getCatPhotos(limit: Long, id: String, apiKey: String): Resource<Call<List<Image>>>{
        val response = try {
            api.getCatPhotos(id, limit, apiKey)
        } catch (e: Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}