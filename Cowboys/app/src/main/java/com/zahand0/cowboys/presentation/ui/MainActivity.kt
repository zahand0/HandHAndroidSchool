package com.zahand0.cowboys.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.zahand0.cowboys.R
import com.zahand0.cowboys.presentation.ui.screen.catalog.CatalogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fitContentViewToInsets()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add<CatalogFragment>(R.id.container)
//                add(R.id.container, ProductFragment.newInstance(StubData.products[0].id))
            }
        }
    }

    private fun fitContentViewToInsets() {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}