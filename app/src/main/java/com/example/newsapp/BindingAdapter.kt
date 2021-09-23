package com.example.newsapp

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.model.Article
import com.example.newsapp.ui.ApiStatus
import com.example.newsapp.ui.NewsAdapter
import java.text.SimpleDateFormat


@BindingAdapter("imageUri")
fun bindImage(imageView: ImageView, imageUri: String?) {
    imageUri?.let {
        val imageUri = imageUri.toUri().buildUpon().scheme("https").build()
        imageView.load(imageUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("ApiStatus")
fun bindStatus(statusProgressBar: ProgressBar, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusProgressBar.visibility = View.VISIBLE
        }
        ApiStatus.ERROR -> {
            statusProgressBar.visibility = View.VISIBLE
        }
        ApiStatus.DONE -> {
            statusProgressBar.visibility = View.GONE
        }
    }
}

@BindingAdapter("textFormatted")
fun formatText(textView: TextView, time: String) {
    val parser: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter: SimpleDateFormat = SimpleDateFormat("EEEE dd MMMM yyyy-HH:mm a")
    val output: String = formatter.format(parser.parse(time))
    textView.text = output
}






