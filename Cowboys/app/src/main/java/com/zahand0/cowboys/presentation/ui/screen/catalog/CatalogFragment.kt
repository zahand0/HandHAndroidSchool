package com.zahand0.cowboys.presentation.ui.screen.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.FragmentCatalogBinding
import com.zahand0.cowboys.presentation.ui.screen.catalog.products.ProductItemDecoration
import com.zahand0.cowboys.presentation.ui.screen.catalog.products.ProductsAdapter
import com.zahand0.cowboys.presentation.ui.util.custom_view.ProgressContainer
import kotlinx.coroutines.launch

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CatalogViewModel by viewModels()
    private val productsAdapter = ProductsAdapter(onBuyClick = {

    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupProducts()
        setupProgressContainerProducts()
        refreshProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupProgressContainerProducts() {
        binding.progressContainerProducts.state = ProgressContainer.State.Loading
        binding.progressContainerProducts.setOnRefreshClickListener {
            binding.progressContainerProducts.state = ProgressContainer.State.Loading
            refreshProducts()
        }
    }

    private fun setupProducts() {
        binding.recyclerProducts.run {
            adapter = productsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        val decorationOffset = requireContext().resources.getDimension(R.dimen.normal_100).toInt()
        val dividerDecoration =
            ProductItemDecoration(requireContext(), R.drawable.products_divider, decorationOffset)

        binding.recyclerProducts.addItemDecoration(dividerDecoration)
    }

    private fun refreshProducts() {
        lifecycleScope.launch {
            viewModel.refreshProducts()
            with(viewModel.products.value) {
                onFailure {
                    binding.progressContainerProducts.state = ProgressContainer.State.Notice(
                        title = getString(R.string.progress_container_unexpected_error_title),
                        description = getString(R.string.progress_container_unexpected_error_description)
                    )
                }
                onSuccess { products ->
                    if (products.isEmpty()) {
                        binding.progressContainerProducts.state =
                            ProgressContainer.State.Notice(
                                title = getString(R.string.progress_container_empty_list_title),
                                description = getString(R.string.progress_container_empty_list_description)
                            )
                    } else {
                        productsAdapter.submitList(products)
                        binding.progressContainerProducts.state =
                            ProgressContainer.State.Success
                    }
                }
            }
        }
    }
}