package com.zahand0.cowboys.di

import com.zahand0.cowboys.data.MockRepository
import com.zahand0.cowboys.domain.repository.Repository
import com.zahand0.cowboys.domain.use_cases.get_active_orders.GetActiveOrdersUseCase
import com.zahand0.cowboys.domain.use_cases.get_active_orders.GetActiveOrdersUseCaseImpl
import com.zahand0.cowboys.domain.use_cases.get_all_orders.GetAllOrdersUseCase
import com.zahand0.cowboys.domain.use_cases.get_all_orders.GetAllOrdersUseCaseImpl
import com.zahand0.cowboys.domain.use_cases.get_all_products.GetAllProductsUseCase
import com.zahand0.cowboys.domain.use_cases.get_all_products.GetAllProductsUseCaseImpl
import com.zahand0.cowboys.domain.use_cases.get_app_version.GetAppVersionUseCase
import com.zahand0.cowboys.domain.use_cases.get_app_version.GetAppVersionUseCaseImpl
import com.zahand0.cowboys.domain.use_cases.get_product_details.GetProductDetailsUseCase
import com.zahand0.cowboys.domain.use_cases.get_product_details.GetProductDetailsUseCaseImpl
import com.zahand0.cowboys.domain.use_cases.get_user.GetUserUseCase
import com.zahand0.cowboys.domain.use_cases.get_user.GetUserUseCaseImpl
import com.zahand0.cowboys.domain.use_cases.sign_in.SignInUseCase
import com.zahand0.cowboys.domain.use_cases.sign_in.SignInUseCaseImpl
import com.zahand0.cowboys.domain.use_cases.sign_out.SignOutUseCase
import com.zahand0.cowboys.domain.use_cases.sign_out.SignOutUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindRepository(repository: MockRepository): Repository

    @Binds
    @Singleton
    fun bindGetAllProductsUseCase(useCase: GetAllProductsUseCaseImpl): GetAllProductsUseCase

    @Binds
    @Singleton
    fun bindGetProductDetailsUseCase(useCase: GetProductDetailsUseCaseImpl): GetProductDetailsUseCase

    @Binds
    @Singleton
    fun bindSignInUseCase(useCase: SignInUseCaseImpl): SignInUseCase

    @Binds
    @Singleton
    fun bindSignOutUseCase(useCase: SignOutUseCaseImpl): SignOutUseCase

    @Binds
    @Singleton
    fun bindGetUserUserCase(useCase: GetUserUseCaseImpl): GetUserUseCase

    @Binds
    @Singleton
    fun bindGetAppVersionUserCase(useCase: GetAppVersionUseCaseImpl): GetAppVersionUseCase

    @Binds
    @Singleton
    fun bindGetAllOrdersUseCase(useCase: GetAllOrdersUseCaseImpl): GetAllOrdersUseCase

    @Binds
    @Singleton
    fun bindGetActiveOrdersUseCase(useCase: GetActiveOrdersUseCaseImpl): GetActiveOrdersUseCase
}