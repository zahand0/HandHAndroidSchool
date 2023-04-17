package com.zahand0.cowboys.presentation.ui.screen.orders

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OrdersScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = NUMBER_OF_TABS

    override fun createFragment(position: Int): Fragment =
        when (position) {
            1 -> OrdersListFragment.newInstance(false)
            else -> OrdersListFragment.newInstance(true)
        }

    companion object {
        private const val NUMBER_OF_TABS = 2
    }
}