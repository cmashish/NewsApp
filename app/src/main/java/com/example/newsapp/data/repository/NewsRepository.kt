package com.example.newsapp.data.repository

import com.example.newsapp.data.model.Article
import com.example.newsapp.di.RetrofitInstance
import javax.inject.Inject

class NewsRepository @Inject constructor() {

    private val api = RetrofitInstance.newsApi

    suspend fun getNews() : List<Article> {
        return api.getNews().articles
    }
}