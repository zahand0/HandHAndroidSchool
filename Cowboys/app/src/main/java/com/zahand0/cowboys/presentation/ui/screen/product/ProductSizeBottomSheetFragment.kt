package com.zahand0.cowboys.presentation.ui.screen.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zahand0.cowboys.databinding.FragmentBottomSheetProductSizesBinding
import com.zahand0.cowboys.domain.model.ProductSizeModel
import com.zahand0.cowboys.presentation.ui.screen.product.adapter.ProductSizesAdapter
import com.zahand0.cowboys.presentation.ui.util.custom_view.ProductSizeCollection

class ProductSizeBottomSheetFragment(
    private val sizes: List<ProductSizeModel>,
    private val onItemClick: (index: Int) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetProductSizesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetProductSizesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSizeList()
    }

    private fun setupSizeList() {
        val productSizes = ProductSizeCollection(requireContext())
        val sizesAdapter = ProductSizesAdapter(
            onItemClick = onItemClick
        )
        productSizes.adapter = sizesAdapter
        sizesAdapter.submitList(sizes)
        binding.root.addView(
            productSizes
        )
    }
}