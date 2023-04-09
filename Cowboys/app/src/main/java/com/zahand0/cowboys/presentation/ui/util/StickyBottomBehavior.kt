package com.zahand0.cowboys.presentation.ui.util

import android.view.View
import android.view.View.SCROLL_AXIS_VERTICAL
import androidx.coordinatorlayout.widget.CoordinatorLayout
import kotlin.math.min

class StickyBottomBehavior(private val anchorId: Int) :
    CoordinatorLayout.Behavior<View>() {
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        val anchor: View = coordinatorLayout.findViewById(anchorId)
        val anchorLocation = IntArray(2)
        anchor.getLocationInWindow(anchorLocation)
        val coordBottom: Int = coordinatorLayout.getBottom()
        //vertical position, cannot scroll over the bottom of the coordinator layout
        child.y = min(anchorLocation[1], coordBottom - child.height).toFloat()
    }
}