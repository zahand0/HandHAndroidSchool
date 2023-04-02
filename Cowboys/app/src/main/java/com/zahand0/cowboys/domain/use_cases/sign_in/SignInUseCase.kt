package com.zahand0.cowboys.domain.use_cases.sign_in

import com.zahand0.cowboys.domain.model.User

interface SignInUseCase {

    suspend operator fun invoke(login: String, password: String): Result<User>
}