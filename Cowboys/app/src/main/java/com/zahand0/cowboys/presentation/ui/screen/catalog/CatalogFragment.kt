package com.zahand0.cowboys.presentation.ui.screen.catalog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.FragmentCatalogBinding
import com.zahand0.cowboys.presentation.ui.screen.catalog.products.ProductsAdapter
import com.zahand0.cowboys.presentation.ui.screen.product.ProductFragment
import com.zahand0.cowboys.presentation.ui.screen.profile.ProfileFragment
import com.zahand0.cowboys.presentation.ui.util.ProductItemDecoration
import com.zahand0.cowboys.presentation.ui.util.custom_view.ProgressContainer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CatalogViewModel by viewModels()
    private val productsAdapter = ProductsAdapter(
        onBuyClick = {},
        onClick = ::onProductClick
    )

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
        setupBarsInsets()
        setupTopBar()
        setupProducts()
        setupProgressContainerProducts()
    }

    private fun setupBarsInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.recyclerProducts) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = insets.bottom
            }
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setupTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    navigateToProfile()
                    true
                }

                else -> false
            }
        }
    }

    private fun navigateToProfile() {
        parentFragmentManager.commit {
            replace<ProfileFragment>(R.id.container)
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupProgressContainerProducts() {
        binding.progressContainerProducts.state = ProgressContainer.State.Loading
        viewLifecycleOwner.lifecycleScope.launch {
            productsAdapter.loadStateFlow.collectLatest { loadStates ->
                renderProducts(loadStates)
            }
        }
        binding.progressContainerProducts.setOnRefreshClickListener {
            productsAdapter.refresh()
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

        viewModel.products
            .flowWithLifecycle(lifecycle)
            .onEach {
                Log.d(TAG, "setupProducts flow ")
                productsAdapter.submitData(it)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun onProductClick(productId: String) {
        parentFragmentManager.commit {
            add(R.id.container, ProductFragment.newInstance(productId))
            addToBackStack(null)
        }
    }

    private fun renderProducts(productsState: CombinedLoadStates) {
        when (val state = productsState.refresh) {
            is LoadState.Loading -> {
                binding.progressContainerProducts.state = ProgressContainer.State.Loading
            }

            is LoadState.Error -> {
                Log.e(TAG, "renderProducts", state.error)
                binding.progressContainerProducts.state = ProgressContainer.State.Notice(
                    title = getString(R.string.progress_container_unexpected_error_title),
                    description = getString(R.string.progress_container_unexpected_error_description)
                )
            }

            is LoadState.NotLoading -> {
                if (productsAdapter.itemCount == 0) {
                    binding.progressContainerProducts.state =
                        ProgressContainer.State.Notice(
                            title = getString(R.string.progress_container_empty_list_title),
                            description = getString(R.string.progress_container_empty_list_description)
                        )
                } else {
                    binding.progressContainerProducts.state =
                        ProgressContainer.State.Success
                }
            }
        }
    }

    companion object {
        private const val TAG = "CatalogFragment"
    }
}