package com.zahand0.cowboys.presentation.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zahand0.cowboys.domain.model.User
import com.zahand0.cowboys.domain.use_cases.get_app_version.GetAppVersionUseCase
import com.zahand0.cowboys.domain.use_cases.get_user.GetUserUseCase
import com.zahand0.cowboys.presentation.ui.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getAppVersionUseCase: GetAppVersionUseCase
) : ViewModel() {

    val appVersion = getAppVersionUseCase()

    private val _user = MutableStateFlow<ResourceState<User>>(ResourceState.Loading)
    val user get() = _user.asStateFlow()

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = ResourceState.Loading
            val result = getUserUseCase()
            result.onFailure {
                _user.value = ResourceState.Error(it)
            }
            result.onSuccess {
                _user.value = ResourceState.Success(it)
            }
        }
    }
}