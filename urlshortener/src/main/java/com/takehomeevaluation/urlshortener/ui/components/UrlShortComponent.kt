package com.takehomeevaluation.urlshortener.ui.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.takehomeevaluation.core.extensions.layoutInflater
import com.takehomeevaluation.urlshortener.R
import com.takehomeevaluation.urlshortener.databinding.UrlshortenerComponentBinding

class UrlShortComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0,
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {

    private val binding = UrlshortenerComponentBinding.inflate(context.layoutInflater, this, false)

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.urlshortened_styleable,
            0, 0,
        ).use(::setupStyledAttr)
    }

    private fun setupStyledAttr(typedArray: TypedArray) {
        binding.title.text = typedArray.getString(R.styleable.urlshortened_styleable_urlLabel) ?: ""
    }

    fun setUrlContent(urlContent: String) {
        binding.content.text = urlContent
    }
}