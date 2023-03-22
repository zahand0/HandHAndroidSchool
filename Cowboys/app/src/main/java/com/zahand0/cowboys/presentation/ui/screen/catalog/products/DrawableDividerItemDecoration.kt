package com.zahand0.cowboys.presentation.ui.screen.catalog.products

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ProductItemDecoration(
    context: Context,
    resId: Int,
    private val offset: Int
) : RecyclerView.ItemDecoration() {

    private var mDivider = ContextCompat.getDrawable(context, resId)

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        mDivider?.let { divider ->
            for (i in 0 until parent.childCount - 1) {
                val child: View = parent.getChildAt(i)

                val params = child.layoutParams as RecyclerView.LayoutParams

                val dividerTop: Int = child.bottom + params.bottomMargin
                val dividerBottom: Int = dividerTop + divider.intrinsicHeight

                divider.setBounds(0, dividerTop, parent.width, dividerBottom)
                divider.draw(c)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.indexOfChild(view) == 0)
            outRect.top = offset
        if (parent.indexOfChild(view) == parent.childCount - 1)
            outRect.bottom = offset
    }

}