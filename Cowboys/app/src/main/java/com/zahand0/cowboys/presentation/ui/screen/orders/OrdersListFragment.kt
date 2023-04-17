package com.zahand0.cowboys.presentation.ui.screen.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.FragmentOrdersListBinding
import com.zahand0.cowboys.domain.model.Order
import com.zahand0.cowboys.presentation.ui.util.ProductItemDecoration
import com.zahand0.cowboys.presentation.ui.util.ResourceState
import com.zahand0.cowboys.presentation.ui.util.custom_view.ProgressContainer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class OrdersListFragment : Fragment() {

    private var _binding: FragmentOrdersListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: OrdersAdapter

    private val viewModel: OrdersViewModel by activityViewModels()
//        ownerProducer = { requireParentFragment() }


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
    }

    private fun setupAdapter() {
        binding.recyclerOrders.layoutManager =
            LinearLayoutManager(requireContext(), VERTICAL, false)
        adapter = OrdersAdapter(
            {}, {}
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

    private fun renderProgressContainer(resourceState: ResourceState<List<Order>>) {
        binding.progressContainerOrdersList.state = when (resourceState) {
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
                adapter.submitList(resourceState.data)
                ProgressContainer.State.Success
            }
        }
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