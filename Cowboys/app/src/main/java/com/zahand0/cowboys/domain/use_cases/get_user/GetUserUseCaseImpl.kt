package com.zahand0.cowboys.domain.use_cases.get_user

import com.zahand0.cowboys.domain.model.UserModel
import com.zahand0.cowboys.domain.repository.Repository
import javax.inject.Inject

class GetUserUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetUserUseCase {
    override suspend fun invoke(): Result<UserModel> = repository.getUser()
}