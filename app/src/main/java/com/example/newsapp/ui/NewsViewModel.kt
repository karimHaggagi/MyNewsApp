package com.example.newsapp.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.newsapp.apiService.NewsApi
import com.example.newsapp.model.Article
import com.example.newsapp.model.News
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class ApiStatus { LOADING, ERROR, DONE }
class NewsViewModel:ViewModel() {

    private val _status=MutableLiveData<ApiStatus>()
    val status:LiveData<ApiStatus> get() = _status

    private val news=MutableLiveData<News>()

    val selectedString=MutableLiveData<String?>()

    private val _article=MutableLiveData<List<Article>>()

    val article:LiveData<List<Article>> get() = Transformations.switchMap(selectedString){ articleString ->
        val articles= when (articleString) {
            null -> _article
            else -> {
                Transformations.switchMap(_article){ articleList ->
                    val filteredArticle=MutableLiveData<List<Article>>()
                    val filteredList=articleList.filter { article ->
                        article.title.toLowerCase().contains(selectedString.value!!.toLowerCase())
                    }
                    filteredArticle.value=filteredList
                    filteredArticle
                }
            }
        }
        articles
    }

    init {
        selectedString.value=null
        getNews()
    }
    //use coroutine
    /*
    private fun getNews(){
        _status.value=ApiStatus.LOADING
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    news.postValue(NewsApi.retrofitService.getNews())
                }
                _article.value= news.value!!.articles
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.d("error",e.message.toString())
                _status.value = ApiStatus.ERROR
                _article.value= listOf()
            }
        }
    }*/

    //use rxjava

    private fun getNews(){
        _status.value=ApiStatus.LOADING
        val observable = NewsApi.retrofitService.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        val observer=object :Observer<News>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: News) {
                news.postValue(t)
                _article.postValue(t.articles)
                _status.postValue(ApiStatus.DONE)

            }

            override fun onError(e: Throwable) {
                _status.postValue(ApiStatus.ERROR)

            }

            override fun onComplete() {
            }
        }
        observable.subscribe(observer)
    }
}








