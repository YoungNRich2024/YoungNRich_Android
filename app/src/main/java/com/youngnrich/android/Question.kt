package com.youngnrich.android

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Question(
    @StringRes val textResId: Int? = null,
    @DrawableRes val drawableRes: Int? = null,
    val realAnswerResId: Int? = null,
    val realAnswer: Boolean? = null,
    var isAnswered: Boolean = false,
)