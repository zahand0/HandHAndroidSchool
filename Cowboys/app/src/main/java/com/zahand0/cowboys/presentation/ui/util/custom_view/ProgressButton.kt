package com.zahand0.cowboys.presentation.ui.util.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.zahand0.cowboys.R
import kotlin.properties.Delegates

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val button: Button
    private val progressBar: ProgressBar

    init {
        inflate(context, R.layout.view_progress_button, this)
        button = findViewById(R.id.button)
        progressBar = findViewById(R.id.progressBar)
        context.withStyledAttributes(attrs, R.styleable.ProgressButton) {
            initializeText()
        }
    }

    var isLoading: Boolean by Delegates.observable(false) { _, _, isLoading ->
        progressBar.isVisible = isLoading
        button.isClickable = !isLoading
        button.textScaleX = if (isLoading) 0f else 1f
    }

    fun setText(resId: Int) {
        button.setText(resId)
    }

    fun setText(text: String) {
        button.text = text
    }

    override fun setOnClickListener(l: OnClickListener?) {
        button.setOnClickListener(l)
    }

    private fun TypedArray.initializeText() {
        val buttonText = this.getString(R.styleable.ProgressButton_android_text) ?: ""
        button.text = buttonText
    }
}