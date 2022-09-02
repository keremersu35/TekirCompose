package com.example.tekirkotlin.di

import com.example.tekirkotlin.repository.CatRepository
import com.example.tekirkotlin.service.ApiInterface
import com.example.tekirkotlin.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCatRepository(
        api: ApiInterface
    ) = CatRepository(api)

    @Singleton
    @Provides
    fun provideCatApi(): ApiInterface{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
    }
}