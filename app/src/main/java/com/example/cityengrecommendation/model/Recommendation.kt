package com.example.cityengrecommendation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Recommendation(
    val id: Int,
    @StringRes val titleResourceId: Int,
    @StringRes val subtitleResourceId: Int,
    @StringRes val description: Int,
    @DrawableRes val imageResourceId: Int,


)