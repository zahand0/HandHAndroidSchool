package com.zahand0.task1.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.zahand0.task1.R
import com.zahand0.task1.presentation.ui.signin.SignInFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fitContentViewToInsets()

        supportFragmentManager.commit {
            add<SignInFragment>(R.id.container)
        }
    }

    private fun fitContentViewToInsets() {
        WindowCompat.setDecorFitsSystemWindows(window,false)
    }
}