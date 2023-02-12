package com.zahand0.task3

import android.os.Bundle
import android.widget.Button
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button

    private lateinit var buttonA: Button
    private lateinit var buttonB: Button
    private lateinit var buttonC: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(
                    R.id.fragment_container_number,
                    MainFragment.newInstance(getString(R.string.one))
                )
                add(
                    R.id.fragment_container_letter,
                    MainFragment.newInstance(getString(R.string.a))
                )
            }
        }
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)

        buttonA = findViewById(R.id.buttonA)
        buttonB = findViewById(R.id.buttonB)
        buttonC = findViewById(R.id.buttonC)

        button1.setOnClickListener {
            replaceMainFragment(getString(R.string.one), R.id.fragment_container_number)
        }
        button2.setOnClickListener {
            replaceMainFragment(getString(R.string.two), R.id.fragment_container_number)
        }
        button3.setOnClickListener {
            replaceMainFragment(getString(R.string.three), R.id.fragment_container_number)
        }

        buttonA.setOnClickListener {
            replaceMainFragment(getString(R.string.a), R.id.fragment_container_letter)
        }
        buttonB.setOnClickListener {
            replaceMainFragment(getString(R.string.b), R.id.fragment_container_letter)
        }
        buttonC.setOnClickListener {
            replaceMainFragment(getString(R.string.c), R.id.fragment_container_letter)
        }
    }

    private fun replaceMainFragment(text: String, @IdRes viewId: Int) {
        supportFragmentManager.commit {
            replace(
                viewId,
                MainFragment.newInstance(text)
            )
            addToBackStack(null)
        }
    }
}