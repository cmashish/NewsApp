package com.example.newsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val _news = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> get() = _news

    private val _selectedArticle = MutableLiveData<Article?>()
    val selectedArticle: LiveData<Article?> = _selectedArticle

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage


    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {

            try {
                _news.value = newsRepository.getNews()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load news"
            } finally {
                _isLoading.value = false
            }
        }
    }
}