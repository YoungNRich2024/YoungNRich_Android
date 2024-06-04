package com.youngnrich.android

import androidx.annotation.StringRes

data class Question(
    @StringRes val textResId: Int? = null,
    val realAnswerResId: Int,
    var isAnswered: Boolean = false,
)