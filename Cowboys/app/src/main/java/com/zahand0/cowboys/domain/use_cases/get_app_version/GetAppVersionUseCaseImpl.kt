package com.zahand0.cowboys.domain.use_cases.get_app_version

import com.zahand0.cowboys.BuildConfig
import javax.inject.Inject

class GetAppVersionUseCaseImpl @Inject constructor() : GetAppVersionUseCase {
    override fun invoke(): String {
        val versionCode: Int = BuildConfig.VERSION_CODE
        val versionName: String = BuildConfig.VERSION_NAME
        return "$versionName ($versionCode)"
    }
}