package com.zahand0.cowboys.presentation.ui.util.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.ViewProgressImageButtonBinding
import kotlin.properties.Delegates

class ProgressImageButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewProgressImageButtonBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    init {
        context.withStyledAttributes(attrs, R.styleable.ProgressImageButton) {
            initializeSourceImage()
        }
    }

    var isLoading: Boolean by Delegates.observable(false) { _, _, isLoading ->
        binding.progressBar.isVisible = isLoading
        binding.button.isClickable = !isLoading
        binding.button.isVisible = !isLoading
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.button.setOnClickListener(l)
    }

    private fun TypedArray.initializeSourceImage() {
        val image =
            this.getResourceId(R.styleable.ProgressImageButton_android_src, R.drawable.ic_more)
        binding.button.setImageResource(image)
    }
}