package com.zahand0.task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener

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