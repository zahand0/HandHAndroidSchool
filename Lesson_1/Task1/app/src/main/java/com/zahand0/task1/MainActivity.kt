package com.zahand0.task1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val redButton = findViewById<Button>(R.id.red_button)
        val blueButton = findViewById<Button>(R.id.blue_button)
        val greenButton = findViewById<Button>(R.id.green_button)

        redButton.setOnClickListener {
            startAnotherActivity(RedActivity::class.java)
        }

        greenButton.setOnClickListener {
            startAnotherActivity(GreenActivity::class.java)
        }

        blueButton.setOnClickListener {
            startAnotherActivity(BlueActivity::class.java)
        }
    }

    private fun <T> startAnotherActivity(cls: Class<T>) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }
}