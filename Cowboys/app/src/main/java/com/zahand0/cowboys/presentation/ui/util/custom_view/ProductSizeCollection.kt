package com.zahand0.cowboys.presentation.ui.util.custom_view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zahand0.cowboys.R

class ProductSizeCollection @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.bottom_sheet_product_sizes, this)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

}

