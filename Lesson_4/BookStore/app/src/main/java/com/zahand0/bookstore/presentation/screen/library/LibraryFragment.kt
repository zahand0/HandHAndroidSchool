package com.zahand0.bookstore.presentation.screen.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zahand0.bookstore.R
import com.zahand0.bookstore.databinding.FragmentLibraryBinding
import com.zahand0.bookstore.presentation.screen.library.book_list_adapter.BookListAdapter
import com.zahand0.bookstore.presentation.util.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding

    private val viewModel by viewModels<LibraryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBookList()
        setupButton()
    }

    private fun setupButton() {
        binding.btnRefresh.setOnClickListener {
            viewModel.refreshBooks()
        }
    }

    private fun setupBookList() {
        val bookListAdapter = BookListAdapter()
        binding.rvBookList.adapter = bookListAdapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.bookList.collectLatest { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            binding.textError.text = requireContext().getString(
                                R.string.library_request_error,
                                resource.message
                            )
                            binding.textError.visibility = View.VISIBLE
                        }

                        is Resource.Loading -> {
                            binding.textError.visibility = View.GONE
                        }

                        is Resource.Success -> {
                            binding.textError.visibility = View.GONE
                        }
                    }
                    resource.data?.let {
                        bookListAdapter.submitList(resource.data.authorsBibliography)
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "LibraryFragment"
    }
}
