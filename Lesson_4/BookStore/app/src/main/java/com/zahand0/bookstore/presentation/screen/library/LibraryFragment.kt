package com.zahand0.bookstore.presentation.screen.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zahand0.bookstore.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding

    private lateinit var viewModel: LibraryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

}
