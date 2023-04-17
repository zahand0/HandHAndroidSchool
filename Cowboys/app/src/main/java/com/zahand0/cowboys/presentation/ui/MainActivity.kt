package com.zahand0.cowboys.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.zahand0.cowboys.R
import com.zahand0.cowboys.presentation.ui.screen.orders.OrdersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fitContentViewToInsets()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add<OrdersFragment>(R.id.container)
//                add<CatalogFragment>(R.id.container)
//                add(R.id.container, ProductFragment.newInstance(StubData.products[0].id))
            }
        }
    }

    private fun fitContentViewToInsets() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}