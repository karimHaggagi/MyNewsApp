package com.example.newsapp.ui

import androidx.lifecycle.*
import com.example.newsapp.apiService.NewsApi
import com.example.newsapp.model.Article
import com.example.newsapp.model.News
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }
class NewsViewModel:ViewModel() {

    private val _status=MutableLiveData<ApiStatus>()
    val status:LiveData<ApiStatus> get() = _status

    private val news=MutableLiveData<News>()

    private val _article=MutableLiveData<List<Article>>()
    val article:LiveData<List<Article>> get() = _article

    init {
        getNews()
    }

    private fun getNews(){
        _status.value=ApiStatus.LOADING
        viewModelScope.launch {
            try {
                news.value = NewsApi.retrofitService.getNews()
                _article.value= news.value!!.articles
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _article.value= listOf()
            }
        }
    }
}








