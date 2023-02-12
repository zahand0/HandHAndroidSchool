package com.zahand0.task3

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class MainFragment : Fragment(R.layout.fragment_main) {
    companion object {
        private const val ARG_MESSAGE_TEXT = "argument_message_text"

        fun newInstance(text: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MESSAGE_TEXT, text)
                }
            }
    }

    private lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.textView)
        textView.text = arguments?.getString(ARG_MESSAGE_TEXT) ?: ""
    }
}