package com.takehomeevaluation.urlshortener.ui.components

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.takehomeevaluation.urlshortener.R
import com.takehomeevaluation.utils.LayoutDimensionEnum
import io.kotlintest.shouldBe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
internal class UrlComponentTest {

    private lateinit var activity: Activity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(Activity::class.java).setup().get()
    }

    @Test
    fun `GIVEN urlLabel WHEN UrlComponent call THEN urlComponent titleItem layout attributes validated`() {
        //GIVEN
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.urlLabel, "URL")
            .build()

        //WHEN
        val urlComponent = UrlComponent(activity, attrs = attrs, 0)

        //THEN
        with(urlComponent.binding.titleItem) {
            text.toString() shouldBe "URL"
            visibility shouldBe View.VISIBLE
            layoutParams.width shouldBe LayoutDimensionEnum.WRAP_CONTENT.value
            layoutParams.height shouldBe LayoutDimensionEnum.WRAP_CONTENT.value
            textSize shouldBe 16f
            currentTextColor shouldBe ContextCompat.getColor(context, R.color.color_text_primary_dark)
            marginTop shouldBe 0
            marginEnd shouldBe 0
            marginStart shouldBe 0
            marginBottom shouldBe 0
        }
    }

    @Test
    fun `GIVEN urlLabel not configured WHEN UrlComponent call THEN urlComponent titleItem layout will take up blank space`() {
        //GIVEN
        val emptyLabel = ""

        //WHEN
        val urlComponent = UrlComponent(activity)

        //THEN
        with(urlComponent.binding.titleItem) {
            text.toString() shouldBe emptyLabel
            visibility shouldBe View.VISIBLE
            layoutParams.width shouldBe LayoutDimensionEnum.WRAP_CONTENT.value
            layoutParams.height shouldBe LayoutDimensionEnum.WRAP_CONTENT.value
            textSize shouldBe 16f
            currentTextColor shouldBe ContextCompat.getColor(context, R.color.color_text_primary_dark)
            marginTop shouldBe 0
            marginEnd shouldBe 0
            marginStart shouldBe 0
            marginBottom shouldBe 0
        }
    }

    @Test
    fun `GIVEN UrlComponent WHEN setUrlContent THEN urlComponent contentItem layout attributes validated`() {
        //GIVEN
        val urlComponent = UrlComponent(activity)

        //WHEN
        urlComponent.setUrlContent(urlContent = "http://google.com")

        //THEN
        with(urlComponent.binding.contentItem) {
            text.toString() shouldBe "http://google.com"
            visibility shouldBe View.VISIBLE
            layoutParams.width shouldBe 0
            layoutParams.height shouldBe LayoutDimensionEnum.WRAP_CONTENT.value
            textSize shouldBe 14f
            currentTextColor shouldBe ContextCompat.getColor(context, R.color.color_text_primary_light)
            marginTop shouldBe 0
            marginEnd shouldBe 0
            marginStart shouldBe 0
            marginBottom shouldBe 0
        }
    }
}