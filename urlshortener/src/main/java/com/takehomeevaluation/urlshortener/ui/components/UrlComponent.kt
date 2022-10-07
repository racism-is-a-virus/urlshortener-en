package com.takehomeevaluation.urlshortener.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.takehomeevaluation.core.extensions.layoutInflater
import com.takehomeevaluation.urlshortener.R
import com.takehomeevaluation.urlshortener.databinding.UrlshortenerUrlComponentBinding


class UrlComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

   private val binding = UrlshortenerUrlComponentBinding.inflate(context.layoutInflater, this)

    init {
        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.UrlShortenedLabelStyleable)
            binding.title.text = attributes.getString(R.styleable.UrlShortenedLabelStyleable_urlLabel) ?: ""
            attributes.recycle()
        }
    }

    fun setUrlContent(urlContent: String) {
        binding.content.text = urlContent
    }
}