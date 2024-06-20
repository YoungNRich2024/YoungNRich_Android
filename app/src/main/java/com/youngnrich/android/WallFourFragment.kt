package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.youngnrich.android.databinding.FragmentWallFourBinding
import com.youngnrich.android.viewmodels.SecondRoomGameViewModel

private const val TAG = "Wall-4 Fragment"

class WallFourFragment : Fragment() {

    private var _binding: FragmentWallFourBinding? = null
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
    ): View? {
        Log.d(TAG, "you're now in Wall 4")

        _binding = FragmentWallFourBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeRoomIllumination()

        binding.apply {
            lampImageButton.setOnClickListener {
                Log.d(TAG, "Lamp is CLICKED!!!")

                // 램프 꺼지게/켜지게 & 방의 조도 바꾸기
                sharedSecondRoomGameViewModel.isLampOn = !sharedSecondRoomGameViewModel.isLampOn

                Log.d(TAG, "Lamp is ON?: ${sharedSecondRoomGameViewModel.isLampOn}")

                changeRoomIllumination()
            }

            windowImageButton.setOnClickListener {
                Log.d(TAG, "Window is CLICKED!!!")

                // 창문 닫히게/열리게 & 방의 조도 바꾸기
                sharedSecondRoomGameViewModel.isWindowOpen = !sharedSecondRoomGameViewModel.isWindowOpen

                Log.d(TAG, "Window is OPEN?: ${sharedSecondRoomGameViewModel.isWindowOpen}")

                changeRoomIllumination()
            }

            baggageImageButton.setOnClickListener {
                Log.d(TAG, "Baggage is CLICKED!!!")

                // 퍼즐 3의 문제지와 비밀번호 입력기가 들어있는 가방 fragment 열기
                secondRoomGameActivity.openFragment(BaggageFragment.newInstance())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "onDestroyView(), Wall-4")

        _binding = null
    }

    private fun changeRoomIllumination() {
        val lamp = sharedSecondRoomGameViewModel.isLampOn
        val window = sharedSecondRoomGameViewModel.isWindowOpen
        binding.apply {
            if (lamp) {
                lampImageButton.setImageResource(R.drawable.lamp_bright)
            } else {
                lampImageButton.setImageResource(R.drawable.lamp_semi_dark)
            }

            if (window) {
                windowImageButton.setImageResource(R.drawable.window_open)
            } else {
                windowImageButton.setImageResource(R.drawable.window_semi_dark)
            }

            if (lamp && window) {
                wallFourSlightlyDimImageView.visibility = View.GONE
            } else if (lamp || window) {
                wallFourSlightlyDimImageView.visibility = View.VISIBLE
                wallFourTooDimImageView.visibility = View.GONE
                windowImportantHintImageView.visibility = View.GONE
            } else {
                wallFourSlightlyDimImageView.visibility = View.GONE
                wallFourTooDimImageView.visibility = View.VISIBLE
                windowImportantHintImageView.visibility = View.VISIBLE

                lampImageButton.setImageResource(R.drawable.lamp_dark)
            }
        }
    }
}