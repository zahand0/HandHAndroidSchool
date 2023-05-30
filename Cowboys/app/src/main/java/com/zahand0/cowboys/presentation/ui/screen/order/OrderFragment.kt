package com.zahand0.cowboys.presentation.ui.screen.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.RoundedCornersTransformation
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.FragmentOrderBinding
import com.zahand0.cowboys.domain.model.ProductDetailsModel
import com.zahand0.cowboys.presentation.ui.util.CurrencyUtil
import com.zahand0.cowboys.presentation.ui.util.ResourceState
import com.zahand0.cowboys.presentation.ui.util.custom_view.ProgressContainer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProgressContainer()
        setupProductListener()
        setupDeliveryDate()
    }

    private fun setupDeliveryDate() {
        binding.layoutOrderDetails.textDeliveryDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment { date ->


            val formatter = DateTimeFormatter.ofPattern("dd MMMM")
            val text = date.format(formatter)
            binding.layoutOrderDetails.textDeliveryDate.setText(text)
        }
        newFragment.show(childFragmentManager, "datePicker")
    }

    private fun setupProductListener() {
        viewModel.product
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                renderProgressContainer(it)
            }
            .launchIn(lifecycleScope)
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
        binding.buttonBuy.isVisible = true
        binding.buttonBuy.setText(
            getString(
                R.string.order_buy_action,
                CurrencyUtil.currencyFormat.format(productDetails.price)
            )
        )
        with(binding.layoutOrderDetails.orderProduct) {
            imageProduct.load(productDetails.images[0]) {
                val corners =
                    binding.root.context.resources.getDimension(R.dimen.product_item_image_corner_size)
                transformations(RoundedCornersTransformation(corners))
            }
            textProductTitle.text = productDetails.title
            textProductCategory.text = productDetails.department
        }
    }

    private fun setupProgressContainer() {
        binding.progressContainerProduct.setOnRefreshClickListener {
            viewModel.refreshProduct(requireArguments().getString(ARG_PRODUCT_ID)!!)
        }
        viewModel.refreshProduct(requireArguments().getString(ARG_PRODUCT_ID)!!)
    }

    companion object {
        private const val ARG_PRODUCT_ID = "argument_product_id"
        private const val ARG_PRODUCT_SIZE = "argument_product_size"

        @JvmStatic
        fun newInstance(productId: String, productSize: String) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PRODUCT_ID, productId)
                    putString(ARG_PRODUCT_SIZE, productSize)
                }
            }
    }
}