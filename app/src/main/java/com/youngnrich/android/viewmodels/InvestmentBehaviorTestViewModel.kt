package com.youngnrich.android.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.youngnrich.android.Answer
import com.youngnrich.android.R

private const val CURRENT_QUESTION_INDEX_KEY = "CURRENT_QUESTION_INDEX_KEY"
private const val SCORE_OF_INVESTMENT_BEHAVIOR_TEST = "SCORE_OF_INVESTMENT_BEHAVIOR_TEST"

class InvestmentBehaviorTestViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        const val MAX_INVESTMENT_BEHAVIOR_TEST_QUESTION_SIZE = 7
    }

    val answers = arrayOf(
        emptyArray(),
        arrayOf(
            Answer(R.string.investment_behavior_test_answer_1_1, 1),
            Answer(R.string.investment_behavior_test_answer_1_2, 2),
            Answer(R.string.investment_behavior_test_answer_1_3, 3),
            Answer(R.string.investment_behavior_test_answer_1_4, 4),
            Answer(R.string.investment_behavior_test_answer_1_5, 5),
        ),
        arrayOf(
            Answer(R.string.investment_behavior_test_answer_2_1, 5),
            Answer(R.string.investment_behavior_test_answer_2_2, 4),
            Answer(R.string.investment_behavior_test_answer_2_3, 3),
            Answer(R.string.investment_behavior_test_answer_2_4, 2),
            Answer(R.string.investment_behavior_test_answer_2_5, 1),
        ),
        arrayOf(
            Answer(R.string.investment_behavior_test_answer_3_1, 1),
            Answer(R.string.investment_behavior_test_answer_3_2, 2),
            Answer(R.string.investment_behavior_test_answer_3_3, 3),
            Answer(R.string.investment_behavior_test_answer_3_4, 4),
            Answer(R.string.investment_behavior_test_answer_3_5, 5),
        ),
        arrayOf(
            Answer(R.string.investment_behavior_test_answer_4_1, 1),
            Answer(R.string.investment_behavior_test_answer_4_2, 2),
            Answer(R.string.investment_behavior_test_answer_4_3, 3),
            Answer(R.string.investment_behavior_test_answer_4_4, 4),
            Answer(R.string.investment_behavior_test_answer_4_5, 5),
            Answer(R.string.investment_behavior_test_answer_4_6, 6),
        ),
        arrayOf(
            Answer(R.string.investment_behavior_test_answer_5_1, 1),
            Answer(R.string.investment_behavior_test_answer_5_2, 3),
            Answer(R.string.investment_behavior_test_answer_5_3, 5),
        ),
        arrayOf(
            Answer(R.string.investment_behavior_test_answer_6_1, 5),
            Answer(R.string.investment_behavior_test_answer_6_2, 1),
        ),
        arrayOf(
            Answer(R.string.investment_behavior_test_answer_7_1, 1),
            Answer(R.string.investment_behavior_test_answer_7_2, 2),
            Answer(R.string.investment_behavior_test_answer_7_3, 3),
            Answer(R.string.investment_behavior_test_answer_7_4, 4),
            Answer(R.string.investment_behavior_test_answer_7_5, 5),
        ),
    )

    var currentQuestionIndex: Int
        get() = savedStateHandle.get(CURRENT_QUESTION_INDEX_KEY) ?: 1
        set(value) = savedStateHandle.set(CURRENT_QUESTION_INDEX_KEY, value)

    var scoreOfInvestmentBehaviorTest: Int
        get() = savedStateHandle.get(SCORE_OF_INVESTMENT_BEHAVIOR_TEST) ?: 0
        set(value) = savedStateHandle.set(SCORE_OF_INVESTMENT_BEHAVIOR_TEST, value)

    fun getInvestmentBehaviorTestResult(): Int {
        return when (scoreOfInvestmentBehaviorTest) {
            in 7..11 -> 1
            in 12..18 -> 2
            in 19..24 -> 3
            in 25..31 -> 4
            in 32..36 -> 5
            else -> throw throw IllegalArgumentException("[ERROR] getInvestmentBehaviorTestResult() -- WRONG scoreOfInvestmentBehaviorTest")
        }
    }
}