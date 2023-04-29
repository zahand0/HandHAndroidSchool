package com.zahand0.cowboys.presentation.ui.screen.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.FragmentProfileBinding
import com.zahand0.cowboys.domain.model.UserModel
import com.zahand0.cowboys.presentation.ui.screen.orders.OrdersFragment
import com.zahand0.cowboys.presentation.ui.screen.signin.SignInFragment
import com.zahand0.cowboys.presentation.ui.util.ResourceState
import com.zahand0.cowboys.presentation.ui.util.custom_view.ProgressContainer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTopBar()
        setupButtons()
        setupAppVersion()
        setupProgressContainer()
    }

    private fun setupTopBar() {
        binding.topAppBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupProgressContainer() {
        viewModel.user
            .flowWithLifecycle(lifecycle)
            .onEach {
                renderProgressContainer(it)
            }
            .launchIn(lifecycleScope)
    }

    private fun renderProgressContainer(resourceState: ResourceState<UserModel>) {
        binding.progressContainerUser.state = when (resourceState) {
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
                setupUserInfo(resourceState.data)
                ProgressContainer.State.Success
            }
        }
    }

    private fun setupAppVersion() {
        binding.textAppVersion.text = getString(R.string.profile_app_version, viewModel.appVersion)
    }

    private fun setupUserInfo(user: UserModel) {
        with(binding) {
            imageProfileAvatar.load(user.avatarUrl) {
                placeholder(R.drawable.profile_img_placeholder)
                transformations(CircleCropTransformation())
            }
            textName.text = getString(R.string.profile_user_name, user.name, user.surname)
            textOccupation.text = user.occupation
        }
    }

    private fun setupButtons() {
        with(binding.layoutCardButtons) {
            buttonOrders.setOnClickListener { navigateToOrders() }
            buttonSettings.setOnClickListener { }
            buttonSignOut.setOnClickListener { navigateToSignIn() }
        }
    }

    private fun navigateToSignIn() {
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.commit {
            replace<SignInFragment>(R.id.container)
        }
    }

    private fun navigateToOrders() {
        parentFragmentManager.commit {
            replace<OrdersFragment>(R.id.container)
        }
    }
}