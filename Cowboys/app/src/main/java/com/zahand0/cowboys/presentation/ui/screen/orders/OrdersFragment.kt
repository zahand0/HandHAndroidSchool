package com.zahand0.cowboys.presentation.ui.screen.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.map
import com.google.android.material.tabs.TabLayoutMediator
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.FragmentOrdersBinding
import com.zahand0.cowboys.presentation.ui.screen.catalog.CatalogFragment
import com.zahand0.cowboys.presentation.ui.util.custom_view.ProgressContainer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrdersViewModel by activityViewModels()

    private lateinit var adapterAllOrders: OrdersAdapter
    private lateinit var adapterActiveOrders: OrdersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        setupPager()
        setupTabs()
        setupProgressContainer()
    }

    private fun setupAdapters() {
        adapterAllOrders = OrdersAdapter({}, {})
        adapterActiveOrders = OrdersAdapter({}, {})
    }

    private fun setupProgressContainer() {
        viewModel.allOrders.flowWithLifecycle(lifecycle).onEach { pagingData ->
            adapterAllOrders.submitData(pagingData.map { it.toOrderState() })
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.activeOrders.flowWithLifecycle(lifecycle).onEach { pagingData ->
            adapterActiveOrders.submitData(pagingData.map { it.toOrderState() })
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            adapterAllOrders.loadStateFlow.collectLatest { loadStates ->
                renderOrders(loadStates)
            }
        }

        binding.progressContainerOrderTabs
            .setButtonText(getString(R.string.orders_empty_list_catalog_action))
        binding.progressContainerOrderTabs.setOnRefreshClickListener {
            parentFragmentManager.commit {
                add<CatalogFragment>(R.id.container)
                addToBackStack(null)
            }
        }
    }

    private fun setupPager() {
        binding.pager.adapter = OrdersScreenSlidePagerAdapter(requireActivity())
    }

    private fun renderOrders(ordersState: CombinedLoadStates) {
        when (ordersState.refresh) {
            is LoadState.Loading, is LoadState.Error -> {
                ProgressContainer.State.Success
            }

            is LoadState.NotLoading -> {
                if (adapterAllOrders.itemCount == 0) {
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



    private fun setupTabs() {
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.tab_all_orders, "")
                }

                1 -> {
                    tab.text = getString(R.string.tab_active_orders, "")
                }
            }
        }.attach()
        setupOrdersTab(adapterAllOrders, 0, R.string.tab_all_orders)
        setupOrdersTab(adapterActiveOrders, 1, R.string.tab_active_orders)
    }

    private fun setupOrdersTab(
        adapter: OrdersAdapter,
        tabNumber: Int,
        stringResId: Int
    ) {
        adapter.loadStateFlow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                val ordersNumber = when (it.refresh) {
                    is LoadState.NotLoading -> {
                        if (adapter.itemCount == 0) "" else adapter.itemCount.toString()
                    }

                    else -> ""
                }
                binding.tabs.getTabAt(tabNumber)?.text = getString(stringResId, ordersNumber)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}