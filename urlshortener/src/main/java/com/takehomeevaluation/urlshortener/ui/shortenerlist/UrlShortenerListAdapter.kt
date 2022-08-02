package com.takehomeevaluation.urlshortener.ui.shortenerlist

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takehomeevaluation.core.extensions.layoutInflater
import com.takehomeevaluation.urlshortener.R
import com.takehomeevaluation.urlshortener.databinding.UrlshortenerListItemBinding

class UrlShortenerListAdapter(private val context: Context) :
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

    inner class UrlShortenerViewHolder(private val urlshortenerListItemBinding: UrlshortenerListItemBinding) :
        RecyclerView.ViewHolder(urlshortenerListItemBinding.root) {
        fun bind(urlShortenerViewItem: UrlShortenerItemView) {
            with(urlshortenerListItemBinding) {
                urlTitleItem.text = context.getString(R.string.urlshortener_title_url)
                urlContentItem.text = urlShortenerViewItem.originalUrl
                shortTitleItem.text = context.getString(R.string.urlshortener_title_short)
                shortContentItem.text = urlShortenerViewItem.shortUrl
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