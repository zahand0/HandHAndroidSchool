package com.zahand0.task3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.zahand0.task3.util.Constants

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText = findViewById<TextInputEditText>(R.id.edit_text)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, HelloActivity::class.java)
            if (editText.text.toString().isNotEmpty()) {
                intent.putExtra(Constants.HELLO_ACTIVITY_NAME_KEY, editText.text.toString())
            }
            startActivity(intent)
        }
    }
}