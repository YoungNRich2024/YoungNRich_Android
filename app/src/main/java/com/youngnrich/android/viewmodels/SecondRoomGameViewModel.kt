package com.youngnrich.android.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.youngnrich.android.GameItem
import com.youngnrich.android.Question

private const val TAG = "SecondRoomGameViewModel"

private const val CURRENT_STORAGE_CLOSET_STATE = "CURRENT_STORAGE_CLOSET_STATE"

private const val BLUE_BOOK_USER_ANSWER = "BLUE_BOOK_USER_ANSWER"
private const val GREEN_BOOK_USER_ANSWER = "GREEN_BOOK_USER_ANSWER"
private const val RED_BOOK_USER_ANSWER = "RED_BOOK_USER_ANSWER"
private const val YELLOW_BOOK_USER_ANSWER = "YELLOW_BOOK_USER_ANSWER"

private const val PUZZLE_2_SOLVED = "PUZZLE_2_SOLVED"
private const val PUZZLE_3_SOLVED = "PUZZLE_3_SOLVED"
private const val PUZZLE_4_SOLVED = "PUZZLE_4_SOLVED"
private const val INVESTMENT_BEHAVIOR_TEST_COMPLETE = "INVESTMENT_BEHAVIOR_TEST_COMPLETE"
private const val KEY_COLLECTED = "KEY_COLLECTED"
private const val FINANCIAL_STATEMENTS_COLLECTED = "FINANCIAL_STATEMENTS_COLLECTED"
private const val LAMP_ON_OFF = "LAMP_ON_OFF"
private const val WINDOW_OPEN_CLOSED = "WINDOW_OPEN_CLOSED"
private const val INVESTMENT_BEHAVIOR_TEST_RESULT = "INVESTMENT_BEHAVIOR_TEST_RESULT"
private const val INVESTMENT_BEHAVIOR_TEST_COMPLETE_TIMING = "INVESTMENT_BEHAVIOR_TEST_COMPLETE_TIMING"

class SecondRoomGameViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    val inventoryItems: Array<GameItem?> = arrayOfNulls(6)

    var isStorageClosetOpened: Boolean
        get() = savedStateHandle.get(CURRENT_STORAGE_CLOSET_STATE) ?: false
        set(value) = savedStateHandle.set(CURRENT_STORAGE_CLOSET_STATE, value)

    var isPuzzle2Solved: Boolean
        get() = savedStateHandle.get(PUZZLE_2_SOLVED) ?: false
        set(value) = savedStateHandle.set(PUZZLE_2_SOLVED, value)

    var isPuzzle3Solved: Boolean
        get() = savedStateHandle.get(PUZZLE_3_SOLVED) ?: false
        set(value) = savedStateHandle.set(PUZZLE_3_SOLVED, value)

    var isPuzzle4Solved: Boolean
        get() = savedStateHandle.get(PUZZLE_4_SOLVED) ?: false
        set(value) = savedStateHandle.set(PUZZLE_4_SOLVED, value)

    private val _isInvestmentBehaviorTestCompleteLiveData = MutableLiveData<Boolean>()
    var isInvestmentBehaviorTestComplete: Boolean
        get() = savedStateHandle.get(INVESTMENT_BEHAVIOR_TEST_COMPLETE) ?: false
        set(value) {
            savedStateHandle.set(INVESTMENT_BEHAVIOR_TEST_COMPLETE, value)
            setIsInvestmentBehaviorTestComplete(value)
        }
    val isInvestmentBehaviorTestCompleteLiveData: LiveData<Boolean> get() = _isInvestmentBehaviorTestCompleteLiveData

    private fun setIsInvestmentBehaviorTestComplete(isInvestmentBehaviorTestComplete: Boolean) {
        _isInvestmentBehaviorTestCompleteLiveData.value = isInvestmentBehaviorTestComplete
    }

    var investmentBehaviorTestCompleteTiming: Boolean
        get() = savedStateHandle.get(INVESTMENT_BEHAVIOR_TEST_COMPLETE_TIMING) ?: false
        set(value) = savedStateHandle.set(INVESTMENT_BEHAVIOR_TEST_COMPLETE_TIMING, value)

    var isKeyCollected: Boolean
        get() = savedStateHandle.get(KEY_COLLECTED) ?: false
        set(value) = savedStateHandle.set(KEY_COLLECTED, value)

    var isFinancialStatementsCollected: Boolean
        get() = savedStateHandle.get(FINANCIAL_STATEMENTS_COLLECTED) ?: false
        set(value) = savedStateHandle.set(FINANCIAL_STATEMENTS_COLLECTED, value)

    var isLampOn: Boolean
        get() = savedStateHandle.get(LAMP_ON_OFF) ?: true
        set(value) = savedStateHandle.set(LAMP_ON_OFF, value)

    var isWindowOpen: Boolean
        get() = savedStateHandle.get(WINDOW_OPEN_CLOSED) ?: true
        set(value) = savedStateHandle.set(WINDOW_OPEN_CLOSED, value)

    private val blueBookQuestion = Question(realAnswer = false)
    private val greenBookQuestion = Question(realAnswer = true)
    private val redBookQuestion = Question(realAnswer = true)
    private val yellowBookQuestion = Question(realAnswer = true)

    var blueBookUserAnswer: Boolean
        get() = savedStateHandle.get(BLUE_BOOK_USER_ANSWER) ?: true
        set(value) = savedStateHandle.set(BLUE_BOOK_USER_ANSWER, value)

    var greenBookUserAnswer: Boolean
        get() = savedStateHandle.get(GREEN_BOOK_USER_ANSWER) ?: true
        set(value) = savedStateHandle.set(GREEN_BOOK_USER_ANSWER, value)

    var redBookUserAnswer: Boolean
        get() = savedStateHandle.get(RED_BOOK_USER_ANSWER) ?: true
        set(value) = savedStateHandle.set(RED_BOOK_USER_ANSWER, value)

    var yellowBookUserAnswer: Boolean
        get() = savedStateHandle.get(YELLOW_BOOK_USER_ANSWER) ?: true
        set(value) = savedStateHandle.set(YELLOW_BOOK_USER_ANSWER, value)

    var investmentBehaviorTestResult: Int
        get() = savedStateHandle.get(INVESTMENT_BEHAVIOR_TEST_RESULT) ?: 0
        set(value) = savedStateHandle.set(INVESTMENT_BEHAVIOR_TEST_RESULT, value)

    fun allAnswersCorrect(): Boolean {
        val correct = blueBookQuestion.realAnswer == blueBookUserAnswer
                && greenBookQuestion.realAnswer == greenBookUserAnswer
                && redBookQuestion.realAnswer == redBookUserAnswer
                && yellowBookQuestion.realAnswer == yellowBookUserAnswer

        if (correct)
            Log.d(TAG, "All Answers are CORRECT!!!")
        else
            Log.d(TAG, "Some Answers might be INCORRECT!!!")

        return correct
    }
}