package com.takehomeevaluation.urlshortener.ui.shortenerlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.takehomeevaluation.core.baseclasses.BaseViewModel
import com.takehomeevaluation.urlshortener.model.OriginalUrl
import com.takehomeevaluation.urlshortener.model.ShortenedUrl
import com.takehomeevaluation.urlshortener.usecase.RegisterUrlUseCase

class UrlShortenerListViewModel(private val registerUrlUseCase: RegisterUrlUseCase) : BaseViewModel() {

    private val _shortenedUrlResultList = MutableLiveData<List<UrlShortenerItemView>>()
    val shortenedUrlResultList: LiveData<List<UrlShortenerItemView>> = _shortenedUrlResultList

    private val _buttonSendUrlIsEnable = MutableLiveData<Boolean>()
    val buttonSendUrlIsEnable: LiveData<Boolean> = _buttonSendUrlIsEnable

    private val urlShortenerMutableMap: MutableMap<Int, UrlShortenerItemView> = mutableMapOf()

    fun validateTypedUrl(typedUrl: String) = _buttonSendUrlIsEnable.postValue(typedUrl.isNotEmpty())

    fun registerUrl(sourceUrl: Any?) {
        (sourceUrl as String).apply {
            if (validateToRegisterUrl(sourceUrl = this)) {
                doAsyncWork {
                    val originalUrl = OriginalUrl(url = this)
                    val shortenedUrl = registerUrlUseCase.execute(param = originalUrl)
                    refreshShortenedList(shortenedUrl)
                }
            }
        }
    }

    private fun refreshShortenedList(shortenedUrl: ShortenedUrl) {
        with(shortenedUrl) {
            val itemView = UrlShortenerItemView(originalUrl = urlLinks.originalUrl, shortUrl = urlLinks.shortUrl)
            urlShortenerMutableMap.addShortenedUrlItemToMap(itemView)
            val sortedUrlShortenerItemViewList = urlShortenerMutableMap.toDescendingSortedList()
            _shortenedUrlResultList.postValue(sortedUrlShortenerItemViewList)
        }
    }

    private fun validateToRegisterUrl(sourceUrl: String): Boolean {
        return urlShortenerMutableMap.values.firstOrNull { it.originalUrl == sourceUrl } == null
    }

    private fun MutableMap<Int, UrlShortenerItemView>.addShortenedUrlItemToMap(itemView: UrlShortenerItemView) {
        if (isEmpty()) this[0] = itemView
        else this[keys.last().inc()] = itemView
    }

    private fun MutableMap<Int, UrlShortenerItemView>.toDescendingSortedList(): List<UrlShortenerItemView> {
        val itemViewMap = toList().sortedByDescending { (key, _) -> key }.toMap()
        return itemViewMap.values.toList()
    }
}