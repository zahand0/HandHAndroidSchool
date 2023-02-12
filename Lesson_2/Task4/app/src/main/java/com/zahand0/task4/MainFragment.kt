package com.zahand0.task4

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.zahand0.task4.util.Constants

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        private const val TAG = "MainFragment"
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.text_view_fragment)
        addStage("onViewCreated")
    }

    override fun onAttach(context: Context) {
        addStage("onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        addStage("onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addStage("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        addStage("onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        addStage("onViewStateRestored")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        addStage("onStart")
        super.onStart()
    }

    override fun onResume() {
        addStage("onResume")
        super.onResume()
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

    override fun onDestroyView() {
        addStage("onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        addStage("onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        addStage("onDetach")
        super.onDetach()
    }
}