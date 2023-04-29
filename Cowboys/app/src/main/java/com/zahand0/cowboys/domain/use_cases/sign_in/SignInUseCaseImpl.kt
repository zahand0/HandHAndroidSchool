package com.zahand0.cowboys.domain.use_cases.sign_in

import com.zahand0.cowboys.domain.model.UserModel
import com.zahand0.cowboys.domain.repository.Repository
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val repository: Repository
) : SignInUseCase {
    override suspend fun invoke(login: String, password: String): Result<UserModel> =
        repository.signIn(login, password)
}