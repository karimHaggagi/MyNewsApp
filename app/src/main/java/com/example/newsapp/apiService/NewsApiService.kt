package com.example.newsapp.apiService

import com.example.newsapp.model.News
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL =
    "https://newsapi.org/v2/"
private val API_KEY="63b1f94dad044add871d1e319c630265"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()


interface NewsApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country:String ="eg",
        @Query("apiKey") apiKey:String= API_KEY
    ):News
}

object NewsApi{
    val retrofitService:NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}








