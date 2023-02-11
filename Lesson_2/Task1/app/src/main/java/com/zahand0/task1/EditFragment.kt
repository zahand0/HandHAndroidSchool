package com.zahand0.task1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult

class EditFragment : Fragment(R.layout.fragment_edit) {

    companion object {
        private const val ARG_EDIT_TEXT = "argument_edit_text"

        const val RESULT_REQUEST_KEY = "result_request_key"
        const val RESULT_BUNDLE_KEY = "result_bundle_key"

        fun newInstance(text: String) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_EDIT_TEXT, text)
                }
            }
    }

    private lateinit var button: Button
    private lateinit var editText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button = view.findViewById(R.id.button)
        editText = view.findViewById(R.id.editText)
        editText.setText(arguments?.getString(ARG_EDIT_TEXT, "") ?: "")
        button.setOnClickListener {
            setFragmentResult(
                requestKey =  RESULT_REQUEST_KEY,
                result = bundleOf(RESULT_BUNDLE_KEY to editText.text.toString())
            )
            parentFragmentManager.popBackStack()
        }
    }

}