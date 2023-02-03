package com.zahand0.task2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linkText = findViewById<TextInputEditText>(R.id.link_text)
        val linkButton = findViewById<Button>(R.id.link_button)
        linkButton.setOnClickListener {
            onButtonClick(
                text = linkText.text.toString(),
                scheme = "https://",
                action = Intent.ACTION_VIEW,
                errorMsg = "Incorrect URL"
            )
        }

        val phoneText = findViewById<TextInputEditText>(R.id.phone_text)
        val phoneButton = findViewById<Button>(R.id.phone_button)
        phoneButton.setOnClickListener {
            onButtonClick(
                text = phoneText.text.toString(),
                scheme = "tel:",
                action = Intent.ACTION_DIAL,
                errorMsg = "Incorrect phone number"
            )
        }
        val geoText = findViewById<TextInputEditText>(R.id.geo_text)
        val geoButton = findViewById<Button>(R.id.geo_button)
        geoButton.setOnClickListener {
            onButtonClick(
                text = geoText.text.toString(),
                scheme = "geo:",
                action = Intent.ACTION_VIEW,
                errorMsg = "Incorrect geo coordinates"
            )
        }
        val emailText = findViewById<TextInputEditText>(R.id.email_text)
        val emailButton = findViewById<Button>(R.id.email_button)
        emailButton.setOnClickListener {
            onButtonClick(
                text = emailText.text.toString(),
                scheme = "mailto:",
                action = Intent.ACTION_SENDTO,
                errorMsg = "Incorrect email"
            )
        }


    }

    private fun onButtonClick(text: String, scheme: String, action: String, errorMsg: String) {
        try {
            var uri = Uri.parse(text)
            if (uri.scheme == null) {
                uri = Uri.parse("$scheme$text")
            }
            Log.d(TAG, "scheme: ${uri.scheme}")
            val intent = Intent(action, uri)
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, errorMsg, e)
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }

}