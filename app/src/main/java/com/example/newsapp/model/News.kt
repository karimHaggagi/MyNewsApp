package com.example.newsapp.model

import com.squareup.moshi.Json


data class News(
    @Json(name ="articles")
    val articles: List<Article>,
    @Json(name ="status")
    val status: String,
    @Json(name ="totalResults")
    val totalResults: Int
)