package com.zahand0.cowboys.di

import android.content.Context
import com.zahand0.cowboys.data.repository.DataStoreOperationsImpl
import com.zahand0.cowboys.data.repository.MockRepository
import com.zahand0.cowboys.domain.repository.DataStoreOperations
import com.zahand0.cowboys.domain.repository.Repository
import com.zahand0.cowboys.domain.use_cases.cancel_order.CancelOrderUseCase
import com.zahand0.cowboys.domain.use_cases.cancel_order.CancelOrderUseCaseImpl
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
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRepository(repository: MockRepository): Repository

    @Binds
    fun bindGetAllProductsUseCase(useCase: GetAllProductsUseCaseImpl): GetAllProductsUseCase

    @Binds
    fun bindGetProductDetailsUseCase(useCase: GetProductDetailsUseCaseImpl): GetProductDetailsUseCase

    @Binds
    fun bindSignInUseCase(useCase: SignInUseCaseImpl): SignInUseCase

    @Binds
    fun bindSignOutUseCase(useCase: SignOutUseCaseImpl): SignOutUseCase

    @Binds
    fun bindGetUserUserCase(useCase: GetUserUseCaseImpl): GetUserUseCase

    @Binds
    fun bindGetAppVersionUserCase(useCase: GetAppVersionUseCaseImpl): GetAppVersionUseCase

    @Binds
    fun bindGetAllOrdersUseCase(useCase: GetAllOrdersUseCaseImpl): GetAllOrdersUseCase

    @Binds
    fun bindGetActiveOrdersUseCase(useCase: GetActiveOrdersUseCaseImpl): GetActiveOrdersUseCase

    @Binds
    fun bindCancelOrderUseCase(useCase: CancelOrderUseCaseImpl): CancelOrderUseCase

    companion object {
        @Provides
        @Singleton
        fun provideDataStoreOperations(
            @ApplicationContext context: Context
        ): DataStoreOperations {
            return DataStoreOperationsImpl(context = context)
        }
    }
}