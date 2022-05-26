package com.example.newsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.LayoutNewsItemBinding
import com.example.newsapp.model.Article
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(val onItemClick: (Article)->Unit)
    :ListAdapter<Article,NewsAdapter.NewsViewHolder>(DiffUtilCallBacks){
    companion object DiffUtilCallBacks:DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return false
        }

    }
    class NewsViewHolder(private var binding: LayoutNewsItemBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun bind(item:Article){
            binding.item=item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutNewsItemBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClick(getItem(position))
        }
    }

}











/*class NewsAdapter(val onItemClick: (Article)->Unit)
    :RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() , Filterable{
    var articleList:List<Article> = listOf()
    var articleFilteredList:List<Article> = listOf()

    fun setData(articleList: List<Article>){
        this.articleList=articleList
        this.articleFilteredList=articleList
        notifyDataSetChanged()
    }
    class NewsViewHolder(private var binding: LayoutNewsItemBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun bind(item:Article){
            binding.item=item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutNewsItemBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articleList[position])
        holder.itemView.setOnClickListener {
            onItemClick(articleList.get(position))
        }
    }

    override fun getItemCount(): Int {
       return articleList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                val filterResult=FilterResults()
                if (charString.isEmpty() ){
                    filterResult.count=articleFilteredList.size
                    filterResult.values=articleFilteredList
                }else{
                    val items=ArrayList<Article>()
                    for (item in articleFilteredList){
                        if(item.title.lowercase(Locale.getDefault())
                                .contains(charString.lowercase(Locale.getDefault()))){
                            items.add(item)
                        }
                    }
                    filterResult.count=items.size
                    filterResult.values=items
                }
                return filterResult
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                if(filterResults.count !=0 ) {
                    articleList = filterResults.values as ArrayList<Article>
                    notifyDataSetChanged()
                }
            }
        }

    }


}*/




















