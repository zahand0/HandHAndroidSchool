package com.zahand0.cowboys.di

import com.zahand0.cowboys.data.remote.CowboysApi
import com.zahand0.cowboys.presentation.ui.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .build()

    private fun provideRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCowboysApi(): CowboysApi =
        provideRetrofitInstance().create(CowboysApi::class.java)

}