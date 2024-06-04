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

    companion object {
        const val MAX_INVENTORY_SIZE = 6
    }

    private var currentIntroIndex: Int
        get() = savedStateHandle.get(CURRENT_INTRO_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INTRO_INDEX_KEY, value)

    val currentIntro: Int
        get() = intros[currentIntroIndex]

    val inventoryItems: MutableList<GameItem> = mutableListOf()

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