package com.zahand0.cowboys.presentation.ui.screen.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.google.android.material.snackbar.Snackbar
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.FragmentOrdersListBinding
import com.zahand0.cowboys.presentation.ui.screen.catalog.CatalogFragment
import com.zahand0.cowboys.presentation.ui.screen.product.ProductFragment
import com.zahand0.cowboys.presentation.ui.util.ProductItemDecoration
import com.zahand0.cowboys.presentation.ui.util.ResourceState
import com.zahand0.cowboys.presentation.ui.util.custom_view.ProgressContainer
import com.zahand0.cowboys.presentation.ui.util.toDateString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class OrdersListFragment : Fragment() {

    private var _binding: FragmentOrdersListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: OrdersAdapter

    private val viewModel: OrdersViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupCancelOrderListener()
        setupProgressContainer()
    }

    private fun setupProgressContainer() {
        binding.progressContainerOrdersList.setOnRefreshClickListener {
            if (requireArguments().getBoolean(ARG_LOAD_ALL_ORDERS)) {
                viewModel.refreshAllOrders()
            } else {
                viewModel.refreshActiveOrders()
            }
        }
    }

    private fun setupCancelOrderListener() {
        viewModel.cancelOrderResultFlow
            .flowWithLifecycle(lifecycle)
            .onEach { result ->
                when (result) {
                    CancelOrderResult.Failure -> {
                        showSnackbar(getString(R.string.order_cancel_error), isError = true)
                    }

                    is CancelOrderResult.Success -> {
                        showSnackbar(
                            getString(
                                R.string.order_cancel_success,
                                result.order.number.toString(),
                                toDateString(result.order.createdAt, "dd.MM.YYYY"),
                                toDateString(result.order.createdAt, "hh:mm")
                            )
                        )
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun setupAdapter() {
        binding.recyclerOrders.layoutManager =
            LinearLayoutManager(requireContext(), VERTICAL, false)
        adapter = OrdersAdapter(
            { orderId ->
                viewModel.cancelOrder(orderId)
            },
            { productId ->
                parentFragmentManager.commit {
                    add(R.id.container, ProductFragment.newInstance(productId))
                    addToBackStack(null)
                }
            }
        )
        binding.recyclerOrders.adapter = adapter
        val decorationOffset = requireContext().resources.getDimension(R.dimen.normal_100).toInt()
        val dividerDecoration =
            ProductItemDecoration(requireContext(), R.drawable.products_divider, decorationOffset)
        binding.recyclerOrders.addItemDecoration(dividerDecoration)

        if (requireArguments().getBoolean(ARG_LOAD_ALL_ORDERS)) {
            viewModel.allOrders.flowWithLifecycle(lifecycle).onEach {
                renderProgressContainer(it)
            }.launchIn(lifecycleScope)
        } else {
            viewModel.activeOrders.flowWithLifecycle(lifecycle).onEach {
                renderProgressContainer(it)
            }.launchIn(lifecycleScope)
        }
    }

    private fun renderProgressContainer(resourceState: ResourceState<List<OrderState>>) {
        binding.progressContainerOrdersList.state = when (resourceState) {
            is ResourceState.Error -> {
                binding.progressContainerOrdersList
                    .setButtonText(getString(R.string.progress_container_refresh_action))
                setupProgressContainer()
                ProgressContainer.State.Notice(
                    getString(R.string.unexpected_error_title),
                    getString(R.string.unexpected_error_description)
                )
            }

            ResourceState.Loading -> {
                ProgressContainer.State.Loading
            }

            is ResourceState.Success -> {
                adapter.submitList(resourceState.data)
                if (resourceState.data.isEmpty()) {
                    binding.progressContainerOrdersList
                        .setButtonText(getString(R.string.orders_empty_list_catalog_action))
                    binding.progressContainerOrdersList.setOnRefreshClickListener {
                        parentFragmentManager.commit {
                            add<CatalogFragment>(R.id.container)
                            addToBackStack(null)
                        }
                    }
                    ProgressContainer.State.Notice(
                        getString(R.string.orders_empty_list_title),
                        getString(R.string.orders_empty_list_description)
                    )
                } else {
                    ProgressContainer.State.Success
                }
            }
        }
    }

    private fun showSnackbar(message: String, isError: Boolean = false) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        )
            .setBackgroundTint(
                if (isError)
                    ContextCompat.getColor(requireContext(), R.color.error)
                else {
                    ContextCompat.getColor(requireContext(), R.color.dark_smalt)
                }
            )
            .show()
    }

    companion object {
        private const val TAG = "OrdersListFragment"
        private const val ARG_LOAD_ALL_ORDERS = "argument_load_all_orders"

        @JvmStatic
        fun newInstance(loadAllOrders: Boolean) =
            OrdersListFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_LOAD_ALL_ORDERS, loadAllOrders)
                }
            }
    }
}