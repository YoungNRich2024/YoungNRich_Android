package com.youngnrich.android

import androidx.annotation.StringRes

data class Answer(
    @StringRes val textResId: Int,
    val point: Int,
)
