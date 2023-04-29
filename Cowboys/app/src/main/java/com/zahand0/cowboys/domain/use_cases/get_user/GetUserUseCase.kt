package com.zahand0.cowboys.domain.use_cases.get_user

import com.zahand0.cowboys.domain.model.UserModel

interface GetUserUseCase {

    suspend operator fun invoke(): Result<UserModel>
}