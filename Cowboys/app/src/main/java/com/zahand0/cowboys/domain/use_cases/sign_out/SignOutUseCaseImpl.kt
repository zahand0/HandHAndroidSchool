package com.zahand0.cowboys.domain.use_cases.sign_out

import com.zahand0.cowboys.domain.repository.Repository
import javax.inject.Inject

class SignOutUseCaseImpl @Inject constructor(
    private val repository: Repository
) : SignOutUseCase {
    override suspend fun invoke() {
        repository.signOut()
    }
}