package com.zahand0.task3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.zahand0.task3.util.Constants

class HelloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val name = intent.getStringExtra(Constants.HELLO_ACTIVITY_NAME_KEY)

        val textView = findViewById<TextView>(R.id.textView)
        textView.text = getString(R.string.greetings).format(name ?: "Stranger")
    }
}