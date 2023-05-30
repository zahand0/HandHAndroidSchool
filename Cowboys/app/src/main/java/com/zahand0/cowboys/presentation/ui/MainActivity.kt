package com.zahand0.cowboys.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.commit
import com.zahand0.cowboys.R
import com.zahand0.cowboys.presentation.ui.screen.order.OrderFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fitContentViewToInsets()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
//                add<SignInFragment>(R.id.container)
//                add<OrdersFragment>(R.id.container)
//                add<CatalogFragment>(R.id.container)
//                add(R.id.container, ProductFragment.newInstance(StubData.products[0].id))
                add(
                    R.id.container, OrderFragment.newInstance(
                        productId = "061f02a0-8d12-4828-ab33-6b319a367e66",
                        productSize = "M"
                    )
                )
            }
        }
    }

    private fun fitContentViewToInsets() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    companion object {
//        "Email": "user1@mail.com"
//        "Password": "12345678"
    }
}