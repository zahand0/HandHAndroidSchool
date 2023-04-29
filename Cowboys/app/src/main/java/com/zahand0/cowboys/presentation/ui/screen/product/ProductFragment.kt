package com.zahand0.cowboys.presentation.ui.screen.product

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.FragmentProductBinding
import com.zahand0.cowboys.domain.model.ProductDetailsModel
import com.zahand0.cowboys.presentation.ui.screen.product.adapter.ProductDetailsAdapter
import com.zahand0.cowboys.presentation.ui.screen.product.adapter.ProductImageCollectionAdapter
import com.zahand0.cowboys.presentation.ui.screen.product.adapter.ProductPreviewImageCollectionAdapter
import com.zahand0.cowboys.presentation.ui.util.CurrencyUtil
import com.zahand0.cowboys.presentation.ui.util.ResourceState
import com.zahand0.cowboys.presentation.ui.util.StickyBottomBehavior
import com.zahand0.cowboys.presentation.ui.util.custom_view.ProgressContainer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()

    private lateinit var productImageCollectionAdapter: ProductImageCollectionAdapter
    private lateinit var productPreviewImageCollectionAdapter: ProductPreviewImageCollectionAdapter
    private lateinit var productDetailsAdapter: ProductDetailsAdapter

    private var productSizeBottomSheetFragment: ProductSizeBottomSheetFragment? = null

    private val onViewPagerPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            try {
                with(binding.layoutProductDetails.recyclerProductPreviews) {
                    smoothScrollToPosition(position)
                    get(viewModel.selectedImageItemIndex.value).isSelected = false
                    get(position).isSelected = true
                }
                viewModel.selectImageItem(position)
            } catch (_: Exception) {
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.layoutProductDetails.pagerProductImage.unregisterOnPageChangeCallback(
            onViewPagerPageChangeCallback
        )
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBarsInsets()
        setupProductImagePager()
        setupProductImagePreviews()
        setupProductDetails()
        setupProgressContainer()
        setupProductListener()
        setupCoordinatorBehavior()
        setupTopBar()
    }

    private fun setupBarsInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.layoutProductDetails.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = insets.bottom
            }
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setupTopBar() {
        binding.topBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupCoordinatorBehavior() {
        val params = binding.buttonBuy.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = StickyBottomBehavior(binding.layoutProductDetails.anchor.id)
    }

    private fun setupProductImagePreviews() {
        productPreviewImageCollectionAdapter = ProductPreviewImageCollectionAdapter(
            onClick = { position ->
                try {
                    with(binding.layoutProductDetails) {
                        pagerProductImage.currentItem = position
                        recyclerProductPreviews[viewModel.selectedImageItemIndex.value].isSelected =
                            false
                        recyclerProductPreviews[position].isSelected =
                            true
                    }

                    viewModel.selectImageItem(position)
                } catch (_: Exception) {
                }
            }
        )
        with(binding.layoutProductDetails.recyclerProductPreviews) {
            adapter = productPreviewImageCollectionAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupProductDetails() {
        productDetailsAdapter = ProductDetailsAdapter()
        with(binding.layoutProductDetails.recyclerProductDetails) {
            adapter = productDetailsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
        }

    }

    private fun setupProgressContainer() {
        binding.progressContainerProduct.setOnRefreshClickListener {
            viewModel.refreshProduct(requireArguments().getString(ARG_PRODUCT_ID)!!)
        }
        viewModel.refreshProduct(requireArguments().getString(ARG_PRODUCT_ID)!!)
    }

    private fun setupProductListener() {
        viewModel.product
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                renderProgressContainer(it)
            }
            .launchIn(lifecycleScope)
    }

    private fun setupProductImagePager() {
        productImageCollectionAdapter = ProductImageCollectionAdapter()
        binding.layoutProductDetails.pagerProductImage.registerOnPageChangeCallback(
            onViewPagerPageChangeCallback
        )
        binding.layoutProductDetails.pagerProductImage.adapter = productImageCollectionAdapter
    }

    private fun renderProgressContainer(resourceState: ResourceState<ProductDetailsModel>) {
        binding.progressContainerProduct.state = when (resourceState) {
            is ResourceState.Error -> {
                ProgressContainer.State.Notice(
                    getString(R.string.unexpected_error_title),
                    getString(R.string.unexpected_error_description)
                )
            }

            ResourceState.Loading -> {
                ProgressContainer.State.Loading
            }

            is ResourceState.Success -> {
                setupProduct(resourceState.data)
                ProgressContainer.State.Success
            }
        }
    }

    private fun setupProduct(productDetails: ProductDetailsModel) {
        setupAdapters(productDetails)
        if (productSizeBottomSheetFragment == null) {
            createBottomSheetFragment(productDetails)
        }
        viewModel.selectedSize
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                binding.layoutProductDetails.textProductSize.setText(productDetails.sizes[it].value)
            }
            .launchIn(lifecycleScope)
        binding.buttonBuy.isVisible = true
        with(binding.layoutProductDetails) {
            textProductDescription.text = productDetails.description
            textProductTitle.text = productDetails.title
            textProductCategory.text = productDetails.department
            textPrice.text = CurrencyUtil.currencyFormat.format(productDetails.price)
            textBadge.text = productDetails.badge.value
            textBadge.backgroundTintList = ColorStateList.valueOf(productDetails.badge.color)
            textProductSize.setOnClickListener {
                productSizeBottomSheetFragment?.show(childFragmentManager, null)
            }
        }
    }

    private fun createBottomSheetFragment(productDetails: ProductDetailsModel) {
        productSizeBottomSheetFragment = ProductSizeBottomSheetFragment(
            sizes = productDetails.sizes,
            onItemClick = {
                viewModel.selectSize(it)
                productSizeBottomSheetFragment?.dismiss()
            })
    }

    private fun setupAdapters(productDetails: ProductDetailsModel) {
        productImageCollectionAdapter.submitList(productDetails.images)
        productDetailsAdapter.submitList(productDetails.details)
        productPreviewImageCollectionAdapter.submitList(productDetails.images)
    }

    companion object {
        private const val ARG_PRODUCT_ID = "argument_product_id"

        @JvmStatic
        fun newInstance(productId: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PRODUCT_ID, productId)
                }
            }
    }
}