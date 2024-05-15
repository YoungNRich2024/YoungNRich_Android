package com.youngnrich.android

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val CURRENT_INTRO_INDEX_KEY = "CURRENT_INTRO_INDEX_KEY"

private val intros = listOf(
    R.string.intro_0, R.string.intro_1, R.string.intro_2,
    R.string.intro_3, R.string.intro_4, R.string.intro_5,
    R.string.intro_6, R.string.intro_7, R.string.intro_8,
)

class YNRViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    private var currentIntroIndex: Int
        get() = savedStateHandle.get(CURRENT_INTRO_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INTRO_INDEX_KEY, value)

    val currentIntro: Int
        get() = intros[currentIntroIndex]

    fun moveToPrevIntro() {
        currentIntroIndex -= 1
    }

    fun moveToNextIntro() {
        currentIntroIndex += 1
    }

    fun isFirstIntro(): Boolean {
        return currentIntroIndex == 0
    }

    fun isLastIntro(): Boolean {
        return currentIntroIndex == intros.size - 1
    }
}