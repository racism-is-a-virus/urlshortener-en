package com.takehomeevaluation.urlshortener.ui.shortenerlist

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takehomeevaluation.core.extensions.layoutInflater
import com.takehomeevaluation.urlshortener.databinding.UrlshortenerListItemBinding

class UrlShortenerListAdapter :
    ListAdapter<UrlShortenerItemView, UrlShortenerListAdapter.UrlShortenerViewHolder>(sectionDiffUtil) {

    private var urlShortenerViewItemList: List<UrlShortenerItemView> = emptyList()

    override fun getItemCount(): Int = urlShortenerViewItemList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitItemList(urlShortenerViewItemList: List<UrlShortenerItemView>) {
        this.urlShortenerViewItemList = urlShortenerViewItemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrlShortenerViewHolder {
        val view = UrlshortenerListItemBinding.inflate(parent.context.layoutInflater, parent, false)
        return UrlShortenerViewHolder(view)
    }

    override fun onBindViewHolder(urlShortenerViewHolder: UrlShortenerViewHolder, position: Int) {
        urlShortenerViewHolder.bind(urlShortenerViewItemList[position])
    }

    inner class UrlShortenerViewHolder(private val itemBinding: UrlshortenerListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(itemView: UrlShortenerItemView) {
            with(itemBinding) {
                originalUrl.setUrlContent(urlContent = itemView.originalUrl)
                shortenedUrl.setUrlContent(urlContent = itemView.shortUrl)
            }
        }
    }

    companion object {
        val sectionDiffUtil = object : DiffUtil.ItemCallback<UrlShortenerItemView>() {
            override fun areContentsTheSame(oldItem: UrlShortenerItemView, newItem: UrlShortenerItemView): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: UrlShortenerItemView, newItem: UrlShortenerItemView): Boolean {
                return oldItem.originalUrl == newItem.originalUrl
            }
        }
    }
}