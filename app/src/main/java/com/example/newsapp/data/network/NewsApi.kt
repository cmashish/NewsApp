package com.example.newsapp.data.network

import com.example.newsapp.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getNews(@Query("country") country: String = "us",
                        @Query("apiKey") apiKey: String = "Add API Key" ) : NewsResponse
}