package com.zahand0.task4

import android.app.Fragment
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zahand0.task4.util.Constants

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var textView: TextView

    private val lifecycleStages = mutableListOf<String>()

    private fun lastLifecycleStages(): String {
        return lifecycleStages.takeLast(Constants.NUMBER_OF_LAST_LIFECYCLE_STAGES)
            .joinToString(separator = "\n")
    }

    private fun addStage(name: String) {
        Log.d(TAG, name)
        lifecycleStages.add(name)
        if (::textView.isInitialized) {
            textView.text = lastLifecycleStages()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.text_view_activity)
        addStage("onCreate")
    }

    @Deprecated("Deprecated in Java")
    override fun onAttachFragment(fragment: Fragment?) {
        addStage("onAttachFragment")
        super.onAttachFragment(fragment)
    }

    override fun onStart() {
        addStage("onStart")
        super.onStart()
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        addStage("onRestoreInstanceState")
        super.onRestoreInstanceState(savedInstanceState, persistentState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        addStage("onPostCreate")
        super.onPostCreate(savedInstanceState)
    }

    override fun onResume() {
        addStage("onResume")
        super.onResume()
    }

    override fun onResumeFragments() {
        addStage("onResumeFragments")
        super.onResumeFragments()
    }

    override fun onPostResume() {
        addStage("onPostResume")
        super.onPostResume()
    }

    override fun onPause() {
        addStage("onPause")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        addStage("onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        addStage("onStop")
        super.onStop()
    }

    override fun onDestroy() {
        addStage("onDestroy")
        super.onDestroy()
    }

    override fun onDetachedFromWindow() {
        addStage("onDetachedFromWindow")
        super.onDetachedFromWindow()
    }
}