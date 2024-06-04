package com.youngnrich.android

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "IpadViewModel"

private const val QUIZ_1_USER_ANSWER = "QUIZ_1_USER_ANSWER"
private const val QUIZ_2_1_USER_ANSWER = "QUIZ_2_1_USER_ANSWER"
private const val QUIZ_2_2_USER_ANSWER = "QUIZ_2_2_USER_ANSWER"
private const val QUIZ_2_3_USER_ANSWER = "QUIZ_2_3_USER_ANSWER"

class IpadViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val question1 = Question(realAnswerResId = R.id.quiz1AnswerDown)
    val question2_1 = Question(realAnswerResId = R.id.quiz2_1AnswerDecrease)
    val question2_2 = Question(realAnswerResId = R.id.quiz2_2AnswerGrow)
    val question2_3 = Question(realAnswerResId = R.id.quiz2_3AnswerRise)

    var quiz1UserAnswerResId: Int?
        get() = savedStateHandle.get(QUIZ_1_USER_ANSWER)
        set(value) = savedStateHandle.set(QUIZ_1_USER_ANSWER, value)

    var quiz2_1UserAnswerResId: Int?
        get() = savedStateHandle.get(QUIZ_2_1_USER_ANSWER)
        set(value) = savedStateHandle.set(QUIZ_2_1_USER_ANSWER, value)

    var quiz2_2UserAnswerResId: Int?
        get() = savedStateHandle.get(QUIZ_2_2_USER_ANSWER)
        set(value) = savedStateHandle.set(QUIZ_2_2_USER_ANSWER, value)

    var quiz2_3UserAnswerResId: Int?
        get() = savedStateHandle.get(QUIZ_2_3_USER_ANSWER)
        set(value) = savedStateHandle.set(QUIZ_2_3_USER_ANSWER, value)

    fun allAnswersCorrect(): Boolean {
        val correct = question1.realAnswerResId == quiz1UserAnswerResId
                && question2_1.realAnswerResId == quiz2_1UserAnswerResId
                && question2_2.realAnswerResId == quiz2_2UserAnswerResId
                && question2_3.realAnswerResId == quiz2_3UserAnswerResId

        if (correct)
            Log.d(TAG, "All Answers are CORRECT!!!")
        else
            Log.d(TAG, "Some Answers might be INCORRECT!!!")

        return correct
    }

    fun isAllQuestion2Answered(): Boolean {
        val question2s = listOf(question2_1, question2_2, question2_3)

        return question2s.all { it.isAnswered }
    }
}