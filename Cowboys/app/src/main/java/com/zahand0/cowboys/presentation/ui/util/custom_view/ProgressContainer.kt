package com.zahand0.cowboys.presentation.ui.util.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import com.zahand0.cowboys.R
import kotlin.properties.Delegates

class ProgressContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val layoutLoading: ViewGroup
    private val layoutNotice: LinearLayout

    init {
        inflate(context, R.layout.view_progress_container, this)
        layoutLoading = findViewById(R.id.layout_loading)
        layoutNotice = findViewById(R.id.layout_notice)
    }

    var state: State by Delegates.observable(State.Loading) { _, _, state ->
        when (state) {
            is State.Loading -> {
                layoutLoading.isVisible = true
                layoutNotice.isVisible = false
                findContentView()?.isVisible = false
            }

            is State.Notice -> {
                layoutLoading.isVisible = false
                layoutNotice.isVisible = true
                findContentView()?.isVisible = false
                with(layoutNotice) {
                    findViewById<TextView>(R.id.text_error_title)?.text = state.title
                    findViewById<TextView>(R.id.text_error_description)?.text = state.description
                }
            }

            is State.Success -> {
                layoutLoading.isVisible = false
                layoutNotice.isVisible = false
                findContentView()?.isVisible = true
            }
        }
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        if (childCount > MAX_CHILD_COUNT) {
            throw IllegalStateException("ProgressContainer can host only one direct child")
        }
    }

    fun setOnRefreshClickListener(onClick: () -> Unit) {
        layoutNotice.findViewById<Button>(R.id.button_refresh)?.setOnClickListener { onClick() }
    }

    fun setButtonText(text: String) {
        layoutNotice.findViewById<Button>(R.id.button_refresh)?.text = text
    }


    private fun findContentView(): View? =
        children.firstOrNull {
            it.id != R.id.layout_loading && it.id != R.id.layout_notice
        }

    sealed class State {
        object Loading : State()
        data class Notice(val title: String, val description: String) : State()
        object Success : State()
    }

    companion object {
        private const val MAX_CHILD_COUNT = 3
    }
}