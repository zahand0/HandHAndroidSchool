package com.zahand0.cowboys.presentation.ui.screen.signin

import android.accounts.AuthenticatorException
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.zahand0.cowboys.R
import com.zahand0.cowboys.databinding.FragmentSignInBinding
import com.zahand0.cowboys.presentation.ui.screen.catalog.CatalogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupLogin()
        setupPassword()
        setupSignInAction()
    }

    private fun setupSignInAction() {
        binding.buttonSignIn.setOnClickListener {
            onSignInClick()
        }
    }

    private fun onSignInClick() {
        closeKeyboard()
        binding.buttonSignIn.isLoading = true
        viewModel.signIn(
            login = binding.textLogin.text?.toString(),
            password = binding.textPassword.text?.toString()
        ) { result ->
            lifecycleScope.launch {
                binding.buttonSignIn.isLoading = false
            }
            result.onFailure {
                when (it) {
                    is IllegalStateException -> {}
                    is AuthenticatorException -> {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.sign_in_wrong_credentials),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.sign_in_unexpected_error),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            result.onSuccess {
                navigateToCatalog()
            }
        }
    }

    private fun navigateToCatalog() {
        parentFragmentManager.commit {
            replace<CatalogFragment>(R.id.container)
            addToBackStack(null)
        }
    }

    private fun setupLogin() {
        binding.textLogin.doOnTextChanged { text, _, _, _ ->
            viewModel.onLoginTextChange(text?.toString())
        }
        viewModel.isLoginValid
            .flowWithLifecycle(lifecycle)
            .onEach { isValid ->
                with(binding.layoutLogin) {
                    if (!isValid) {
                        error = getString(R.string.sign_in_login_error)
                    } else {
                        error = null
                        isErrorEnabled = false
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun setupPassword() {

        binding.textPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordTextChange(text?.toString())
        }
        viewModel.isPasswordValid
            .flowWithLifecycle(lifecycle)
            .onEach { isValid ->
                with(binding.layoutPassword) {
                    if (!isValid) {
                        error = getString(R.string.sign_in_password_error)
                    } else {
                        error = null
                        isErrorEnabled = false
                    }
                }
            }
            .launchIn(lifecycleScope)
        binding.textPassword.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                onSignInClick()
                true
            } else {
                false
            }
        }
    }

    private fun closeKeyboard() {
        val view = requireActivity().currentFocus
        view?.let {
            val manager =
                ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
            manager?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}