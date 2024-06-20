package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.youngnrich.android.databinding.FragmentIpadBinding

private const val TAG = "IpadFragment"

class IpadFragment : Fragment() {

    companion object {
        fun newInstance() = IpadFragment()
    }

    private var _binding: FragmentIpadBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val ipadViewModel: IpadViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "$TAG is open")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIpadBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            ipadBaseNavigation.apply {
                // 양옆 화살표 => IpadFragment 에서는 필요 없음
                baseArrowLeft.visibility = View.GONE
                baseArrowRight.visibility = View.GONE

                // 아래 화살표 => Fragment 끄고 다시 hosting activity 로 회귀
                baseArrowBottom.setOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            // Quiz #1
            quiz1AnswerUp.setOnClickListener {
                ipadViewModel.quiz1UserAnswerResId = quiz1AnswerUp.id
                ipadViewModel.question1.isAnswered = true

                scaleButtons(quiz1AnswerUp, quiz1AnswerDown)

                binding.ipadNextImageButton.visibility = View.VISIBLE
            }

            quiz1AnswerDown.setOnClickListener {
                ipadViewModel.quiz1UserAnswerResId = quiz1AnswerDown.id
                ipadViewModel.question1.isAnswered = true

                scaleButtons(quiz1AnswerDown, quiz1AnswerUp)

                binding.ipadNextImageButton.visibility = View.VISIBLE
            }

            ipadNextImageButton.setOnClickListener {
                binding.ipadQuiz1.visibility = View.GONE
                binding.ipadQuiz2.visibility = View.VISIBLE
            }
            // ---------------------- Quiz #1

            // Quiz #2
            ipadPrevImageButton.setOnClickListener {
                binding.ipadQuiz2.visibility = View.GONE
                binding.ipadQuiz1.visibility = View.VISIBLE
            }

            val toggleGroups = arrayOf(
                quiz21ToggleGroup,
                quiz22ToggleGroup,
                quiz23ToggleGroup,
            )

            toggleGroups.forEach {
                it.addOnButtonCheckedListener { toggleGroup, checkedId, isChecked ->
                    if (isChecked) {
                        when (toggleGroup) {
                            quiz21ToggleGroup -> {
                                ipadViewModel.quiz2_1UserAnswerResId = checkedId
                                ipadViewModel.question2_1.isAnswered = true

                                Log.d(TAG, "quiz2_1UserAnswer: ${view.findViewById<Button>(checkedId).text}")
                            }
                            quiz22ToggleGroup -> {
                                ipadViewModel.quiz2_2UserAnswerResId = checkedId
                                ipadViewModel.question2_2.isAnswered = true

                                Log.d(TAG, "quiz2_2UserAnswer: ${view.findViewById<Button>(checkedId).text}")
                            }
                            quiz23ToggleGroup -> {
                                ipadViewModel.quiz2_3UserAnswerResId = checkedId
                                ipadViewModel.question2_3.isAnswered = true

                                Log.d(TAG, "quiz2_3UserAnswer: ${view.findViewById<Button>(checkedId).text}")
                            }
                        }
                    }

                    if (ipadViewModel.isAllQuestion2Answered()) {
                        ipadSubmitImageButton.visibility = View.VISIBLE
                    }
                }
            }

            ipadSubmitImageButton.setOnClickListener {
                ipadQuiz2.visibility = View.GONE

                if (ipadViewModel.allAnswersCorrect()) {
                    changeIpadQuizResultUiToSuccess()

                    ipadQuizResultImageButton.setOnClickListener {
                        // TODO: 방 이동 움짤 로 넘어가기
                        Log.d(TAG, "Go to Second Room!!!")
                    }
                } else {
                    changeIpadQuizResultUiToFail()

                    ipadQuizResultImageButton.setOnClickListener {
                        ipadQuizResult.visibility = View.GONE
                        ipadQuiz1.visibility = View.VISIBLE
                    }
                }

                ipadQuizResult.visibility = View.VISIBLE
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun changeIpadQuizResultUiToSuccess() {
        binding.apply {
            ipadQuizResultImageView.setImageResource(R.drawable.quiz_success)
            ipadQuizResultTitle.setText(R.string.ipad_quiz_result_title_success)
            ipadQuizResultTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.gold_3))
            ipadQuizResultDetail.setText(R.string.ipad_quiz_result_detail_success)
            ipadQuizResultDetail.setTextColor(ContextCompat.getColor(requireContext(), R.color.gold_3))
            ipadQuizResultImageButton.setImageResource(R.drawable.next_button_gold)
        }
    }

    private fun changeIpadQuizResultUiToFail() {
        binding.apply {
            ipadQuizResultImageView.setImageResource(R.drawable.quiz_fail)
            ipadQuizResultTitle.setText(R.string.ipad_quiz_result_title_fail)
            ipadQuizResultTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            ipadQuizResultDetail.setText(R.string.ipad_quiz_result_detail_fail)
            ipadQuizResultDetail.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            ipadQuizResultImageButton.setImageResource(R.drawable.quiz_button)
        }
    }

    private fun scaleButtons(selectedButton: ImageButton, notSelectedButton: ImageButton) {
        selectedButton.scaleX = 1.1f
        selectedButton.scaleY = 1.1f

        if (notSelectedButton.scaleX == 1.1f) {
            notSelectedButton.scaleX = 1.0f
            notSelectedButton.scaleY = 1.0f
        }
    }
}