package com.zahand0.cowboys.presentation.ui.screen.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zahand0.cowboys.domain.model.UserModel
import com.zahand0.cowboys.domain.use_cases.sign_in.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _isLoginValid = MutableStateFlow(true)
    val isLoginValid get() = _isLoginValid.asStateFlow()

    private val _isPasswordValid = MutableStateFlow(true)
    val isPasswordValid get() = _isPasswordValid.asStateFlow()

    fun onLoginTextChange(text: String?) {
        _isLoginValid.value = isLoginValidOrEmpty(text)
    }

    fun onPasswordTextChange(text: String?) {
        _isPasswordValid.value = isPasswordValidOrEmpty(text)
    }

    private fun isLoginValidOrEmpty(text: String?): Boolean {
        if (text.isNullOrEmpty()) return true
        return isLoginValid(text)
    }

    private fun isLoginValid(text: String?): Boolean =
        text?.matches(Regex("^[\\w-]+@([\\w-]+\\.)+[\\w-]{2,4}\$")) ?: false

    private fun isPasswordValidOrEmpty(text: String?): Boolean {
        if (text.isNullOrEmpty()) return true
        return isPasswordValid(text)
    }

    private fun isPasswordValid(text: String?): Boolean {
        if (text == null) return false
        return text.length >= 8
    }

    fun signIn(
        login: String?,
        password: String?,
        onResultListener: (Result<UserModel>) -> Unit
    ) {
        _isLoginValid.value = isLoginValid(login)
        _isPasswordValid.value = isPasswordValid(password)
        if (!_isLoginValid.value || !_isPasswordValid.value ||
            login == null || password == null
        ) {
            onResultListener(Result.failure(IllegalStateException()))
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            onResultListener(signInUseCase.invoke(login, password))
        }
    }
}