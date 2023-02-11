package com.zahand0.task1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener


class MessageFragment : Fragment(R.layout.fragment_message) {
    companion object {
        private const val ARG_MESSAGE_TEXT = "argument_message_text"

        fun newInstance(text: String) =
            MessageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MESSAGE_TEXT, text)
                }
            }
    }

    private lateinit var button: Button
    private lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button = view.findViewById(R.id.button)
        textView = view.findViewById(R.id.textView)
        textView.text = arguments?.getString(ARG_MESSAGE_TEXT) ?: getString(R.string.default_message)
        button.setOnClickListener {
            parentFragmentManager.commit {
                replace(
                    R.id.fragment_container_view,
                    EditFragment.newInstance(textView.text.toString())
                )
                addToBackStack(null)
            }
        }

        setFragmentResultListener(requestKey = EditFragment.RESULT_REQUEST_KEY) { requestKey, bundle ->
            bundle.getString(EditFragment.RESULT_BUNDLE_KEY)?.let {
                textView.text = it
            }
        }
    }
}