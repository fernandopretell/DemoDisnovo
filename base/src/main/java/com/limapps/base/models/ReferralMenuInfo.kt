package com.limapps.base.models

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

data class ReferralMenuInfo(
        val amount: Double,
        val title: String,
        val subtitle: String,
        @ColorInt val bannerStartColor: Int,
        @ColorInt val bannerEndColor: Int,
        @ColorInt val bannerTextColor: Int,
        @DrawableRes val bannerResource: Int
)