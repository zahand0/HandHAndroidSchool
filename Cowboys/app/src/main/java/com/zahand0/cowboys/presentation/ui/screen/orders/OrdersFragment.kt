package com.zahand0.cowboys.presentation.ui.screen.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.FragmentOrdersBinding
import com.zahand0.cowboys.presentation.ui.util.custom_view.ProgressContainer

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
        binding.progressContainerOrderTabs.state = ProgressContainer.State.Success
    }

    private fun setupPager() {
        binding.pager.adapter = OrdersScreenSlidePagerAdapter(requireActivity())
    }

    private fun setupTabs() {
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.tab_all_orders)
                }

                1 -> {
                    tab.text = getString(R.string.tab_active_orders)
                }
            }
        }.attach()

    }

}