package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.youngnrich.android.databinding.FragmentStorageClosetBinding
import com.youngnrich.android.viewmodels.SecondRoomGameViewModel

private const val TAG = "StorageClosetFragment"

class StorageClosetFragment : Fragment() {

    companion object {
        fun newInstance() = StorageClosetFragment()
    }

    private var _binding: FragmentStorageClosetBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val secondRoomGameActivity: SecondRoomGameActivity
        get() = checkNotNull(activity as? SecondRoomGameActivity) {
            "Cannot access secondRoomGameActivity because it is null."
        }
    private val sharedSecondRoomGameViewModel: SecondRoomGameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStorageClosetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            storageClosetBaseNavigation.apply {
                baseArrowLeft.visibility = View.GONE
                baseArrowRight.visibility = View.GONE

                baseArrowBottom.setOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            if (sharedSecondRoomGameViewModel.blueBookUserAnswer)
                blueBookSwitchImageView.setImageResource(R.drawable.switch_blue_true)
            else
                blueBookSwitchImageView.setImageResource(R.drawable.switch_blue_false)

            if (sharedSecondRoomGameViewModel.greenBookUserAnswer)
                greenBookSwitchImageView.setImageResource(R.drawable.switch_green_true)
            else
                greenBookSwitchImageView.setImageResource(R.drawable.switch_green_false)

            if (sharedSecondRoomGameViewModel.redBookUserAnswer)
                redBookSwitchImageView.setImageResource(R.drawable.switch_red_true)
            else
                redBookSwitchImageView.setImageResource(R.drawable.switch_red_false)

            if (sharedSecondRoomGameViewModel.yellowBookUserAnswer)
                yellowBookSwitchImageView.setImageResource(R.drawable.switch_yellow_true)
            else
                yellowBookSwitchImageView.setImageResource(R.drawable.switch_yellow_false)

            blueBookSwitchTrueButton.setOnClickListener {
                Log.d(TAG, "Blue book's TRUE button is CLICKED!!!")

                // ViewModel 의 Blue book Answer 를 true 로 변경 & 이미지 교체
                sharedSecondRoomGameViewModel.blueBookUserAnswer = true
                blueBookSwitchImageView.setImageResource(R.drawable.switch_blue_true)
            }

            blueBookSwitchFalseButton.setOnClickListener {
                Log.d(TAG, "Blue book's FALSE button is CLICKED!!!")

                // ViewModel 의 Blue book Answer 를 false 로 변경 & 이미지 교체
                sharedSecondRoomGameViewModel.blueBookUserAnswer = false
                blueBookSwitchImageView.setImageResource(R.drawable.switch_blue_false)
            }

            greenBookSwitchTrueButton.setOnClickListener {
                Log.d(TAG, "Green book's TRUE button is CLICKED!!!")

                // ViewModel 의 Green book Answer 를 true 로 변경 & 이미지 교체
                sharedSecondRoomGameViewModel.greenBookUserAnswer = true
                greenBookSwitchImageView.setImageResource(R.drawable.switch_green_true)
            }

            greenBookSwitchFalseButton.setOnClickListener {
                Log.d(TAG, "Green book's FALSE button is CLICKED!!!")

                // ViewModel 의 Green book Answer 를 false 로 변경 & 이미지 교체
                sharedSecondRoomGameViewModel.greenBookUserAnswer = false
                greenBookSwitchImageView.setImageResource(R.drawable.switch_green_false)
            }

            redBookSwitchTrueButton.setOnClickListener {
                Log.d(TAG, "Red book's TRUE button is CLICKED!!!")

                // ViewModel 의 Red book Answer 를 true 로 변경 & 이미지 교체
                sharedSecondRoomGameViewModel.redBookUserAnswer = true
                redBookSwitchImageView.setImageResource(R.drawable.switch_red_true)
            }

            redBookSwitchFalseButton.setOnClickListener {
                Log.d(TAG, "Red book's FALSE button is CLICKED!!!")

                // ViewModel 의 Red book Answer 를 false 로 변경 & 이미지 교체
                sharedSecondRoomGameViewModel.redBookUserAnswer = false
                redBookSwitchImageView.setImageResource(R.drawable.switch_red_false)
            }

            yellowBookSwitchTrueButton.setOnClickListener {
                Log.d(TAG, "Yellow book's TRUE button is CLICKED!!!")

                // ViewModel 의 Yellow book Answer 를 true 로 변경 & 이미지 교체
                sharedSecondRoomGameViewModel.yellowBookUserAnswer = true
                yellowBookSwitchImageView.setImageResource(R.drawable.switch_yellow_true)
            }

            yellowBookSwitchFalseButton.setOnClickListener {
                Log.d(TAG, "Yellow book's FALSE button is CLICKED!!!")

                // ViewModel 의 Yellow book Answer 를 false 로 변경 & 이미지 교체
                sharedSecondRoomGameViewModel.yellowBookUserAnswer = false
                yellowBookSwitchImageView.setImageResource(R.drawable.switch_yellow_false)
            }

            zoomedStorageClosetSubmitButton.setOnClickListener {
                Log.d(TAG, "SUBMIT button is CLICKED!!!")

                // ViewModel 를 통해 퍼즐 2의 최종 답 체크
                val correct = sharedSecondRoomGameViewModel.allAnswersCorrect()

                // 모두 맞았다면 => "뭔가 열렸다"는 정보가 담긴 다이얼로그 바로 띄우기
                // 틀린 게 하나라도 있다면 => "아무 일도 일어나지 않았다"는 정보가 담긴 다이얼로그 바로 띄우기
                showCommonDialog(correct)

                // ViewModel 에서 퍼즐 2를 solved 로 처리하기
                if (correct) {
                    sharedSecondRoomGameViewModel.isPuzzle2Solved = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun showCommonDialog(correct: Boolean) {
        val dialogText = when (correct) {
            true -> R.string.dialog_puzzle_2_success
            false -> R.string.dialog_puzzle_2_fail
        }

        val commonDialog = CommonDialog(dialogText)

        commonDialog.isCancelable = false
        commonDialog.show(secondRoomGameActivity.supportFragmentManager, "CommonDialog")
    }
}