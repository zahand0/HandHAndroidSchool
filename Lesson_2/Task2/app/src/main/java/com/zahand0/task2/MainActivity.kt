package com.zahand0.task2

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {

    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragment_container_view, MainFragment.newInstance(getString(R.string.one)))
            }
        }
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button1.setOnClickListener {
            replaceMainFragment(getString(R.string.one))
        }
        button2.setOnClickListener {
            replaceMainFragment(getString(R.string.two))
        }
        button3.setOnClickListener {
            replaceMainFragment(getString(R.string.three))
        }
    }

    private fun replaceMainFragment(text: String) {
        supportFragmentManager.commit {
            replace(
                R.id.fragment_container_view,
                MainFragment.newInstance(text)
            )
            addToBackStack(null)
        }
    }
}