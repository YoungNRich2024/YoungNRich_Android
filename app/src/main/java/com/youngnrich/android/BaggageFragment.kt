package com.youngnrich.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.youngnrich.android.databinding.FragmentBaggageBinding
import com.youngnrich.android.viewmodels.BaggageViewModel
import com.youngnrich.android.viewmodels.SecondRoomGameViewModel

private const val TAG = "BaggageFragment"

private const val MAX_NUMBER_CLICKED_COUNT = 4
private const val INITIAL_NUMBER_CLICKED_COUNT = 1

class BaggageFragment : Fragment() {

    companion object {
        fun newInstance() = BaggageFragment()
    }

    private var _binding: FragmentBaggageBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val secondRoomGameActivity: SecondRoomGameActivity
        get() = checkNotNull(activity as? SecondRoomGameActivity) {
            "Cannot access secondRoomGameActivity because it is null."
        }
    private val sharedSecondRoomGameViewModel: SecondRoomGameViewModel by activityViewModels()

    private val baggageViewModel: BaggageViewModel by viewModels()

    private var numberClickedCount = 0
    private val numberText = StringBuilder("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaggageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numberClickListener: View.OnClickListener = View.OnClickListener {
            numberClicked(it)
        }

        val deleteClickListener: View.OnClickListener = View.OnClickListener {
            deleteClicked()
        }

        binding.apply {
            baggageBaseNavigation.apply {
                baseArrowLeft.visibility = View.GONE
                baseArrowRight.visibility = View.GONE

                baseArrowBottom.setOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            button1.setOnClickListener(numberClickListener)
            button2.setOnClickListener(numberClickListener)
            button3.setOnClickListener(numberClickListener)
            button4.setOnClickListener(numberClickListener)
            button5.setOnClickListener(numberClickListener)
            button6.setOnClickListener(numberClickListener)
            button7.setOnClickListener(numberClickListener)
            button8.setOnClickListener(numberClickListener)
            button9.setOnClickListener(numberClickListener)

            buttonDelete.setOnClickListener(deleteClickListener)

            puzzle3SheetsImageButton.setOnClickListener {
                secondRoomGameActivity.openFragment(Puzzle3SheetsFragment.newInstance())
            }

            // 만약 이전에 이미 puzzle 3 를 solved 했었다면 => 정답 표시 + SUCCESS 결과 + 키보드 비활성화
            if (sharedSecondRoomGameViewModel.isPuzzle3Solved) {
                passwordDigit1TextView.text = "4"
                passwordDigit2TextView.text = "1"
                passwordDigit3TextView.text = "4"
                passwordDigit4TextView.text = "1"

                puzzle3ResultTextView.apply {
                    setText(R.string.puzzle_3_success)
                    setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.puzzle_3_success_green
                        )
                    )
                }

                disableKeyPad()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    private fun numberClicked(view: View) {
        // todo
        numberClickedCount++

        val numberString = (view as? AppCompatButton)?.text?.toString() ?: ""
        numberText.append(numberString)

        updatePasswordDigits(numberClickedCount, numberString)

        if (numberClickedCount == MAX_NUMBER_CLICKED_COUNT) {
            if (numberText.toString() == BaggageViewModel.PUZZLE_3_ANSWER) {
                binding.puzzle3ResultTextView.apply {
                    setText(R.string.puzzle_3_success)
                    setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.puzzle_3_success_green
                        )
                    )
                }
                sharedSecondRoomGameViewModel.isPuzzle3Solved = true

                // 키패드 비활성화
                disableKeyPad()

                // TODO: 키패드 진동 2번 모션 추가
            } else {
                // 키패드 초기화 처리
                binding.puzzle3ResultTextView.apply {
                    setText(R.string.puzzle_3_fail)
                    setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.puzzle_3_fail_red
                        )
                    )
                }
                numberClickedCount = 0
                numberText.clear()

                // TODO: 키패드 진동 4번 모션 추가
            }
        } else {
            binding.puzzle3ResultTextView.apply {
                setText(R.string.puzzle_3_ing)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.puzzle_3_ing_blue))
            }

            if (numberClickedCount == INITIAL_NUMBER_CLICKED_COUNT) {
                clearPasswordDigits()
            }
        }
    }

    private fun updatePasswordDigits(numberClickedCount: Int, numberString: String) {
        val digitTextView: TextView = when (numberClickedCount) {
            1 -> binding.passwordDigit1TextView
            2 -> binding.passwordDigit2TextView
            3 -> binding.passwordDigit3TextView
            4 -> binding.passwordDigit4TextView
            else -> throw IllegalArgumentException("[ERROR] updatePasswordDigits() -- WRONG numberClickedCount")
        }

        digitTextView.text = numberString
    }

    private fun clearPasswordDigits() {
        binding.apply {
            passwordDigit2TextView.text = ""
            passwordDigit3TextView.text = ""
            passwordDigit4TextView.text = ""
        }
    }

    private fun deleteClicked() {
        if (numberClickedCount > 0 && numberText.isNotEmpty()) {
            updatePasswordDigits(numberClickedCount, "")

            numberClickedCount--

            numberText.deleteAt(numberText.length - 1)
        }
    }

    private fun disableKeyPad() {
        binding.apply {
            button1.isEnabled = false
            button2.isEnabled = false
            button3.isEnabled = false
            button4.isEnabled = false
            button5.isEnabled = false
            button6.isEnabled = false
            button7.isEnabled = false
            button8.isEnabled = false
            button9.isEnabled = false
            buttonDelete.isEnabled = false
        }
    }
}