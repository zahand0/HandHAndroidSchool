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
import com.google.android.material.tabs.TabLayoutMediator
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.FragmentOrdersBinding
import com.zahand0.cowboys.presentation.ui.screen.catalog.CatalogFragment
import com.zahand0.cowboys.presentation.ui.util.ResourceState
import com.zahand0.cowboys.presentation.ui.util.custom_view.ProgressContainer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrdersViewModel by activityViewModels()

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
        setupPager()
        setupTabs()
        setupProgressContainer()
    }

    private fun setupProgressContainer() {
        viewModel.allOrders.flowWithLifecycle(lifecycle).onEach {
            renderProgressContainer(it)
        }.launchIn(lifecycleScope)
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

    private fun renderProgressContainer(resourceState: ResourceState<List<OrderState>>) {
        binding.progressContainerOrderTabs.state = when (resourceState) {
            is ResourceState.Error, ResourceState.Loading -> {
                ProgressContainer.State.Success
            }

            is ResourceState.Success -> {
                if (resourceState.data.isEmpty()) {
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

        viewModel.allOrders.flowWithLifecycle(lifecycle).onEach {
            binding.tabs.getTabAt(0)?.text = when (it) {
                is ResourceState.Error, ResourceState.Loading -> {
                    getString(R.string.tab_all_orders, "")
                }

                is ResourceState.Success -> {
                    val ordersNumber = if (it.data.isNotEmpty()) it.data.size.toString() else ""
                    getString(R.string.tab_all_orders, ordersNumber)
                }
            }
        }.launchIn(lifecycleScope)
        viewModel.activeOrders.flowWithLifecycle(lifecycle).onEach {
            binding.tabs.getTabAt(1)?.text = when (it) {
                is ResourceState.Error, ResourceState.Loading -> {
                    getString(R.string.tab_active_orders, "")
                }

                is ResourceState.Success -> {
                    val ordersNumber = if (it.data.isNotEmpty()) it.data.size.toString() else ""
                    getString(R.string.tab_active_orders, ordersNumber)
                }
            }
        }.launchIn(lifecycleScope)
    }

}