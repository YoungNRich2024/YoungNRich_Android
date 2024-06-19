package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youngnrich.android.databinding.FragmentWallFourBinding

private const val TAG = "Wall-4 Fragment"

class WallFourFragment : Fragment() {
    private var _binding: FragmentWallFourBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        binding.apply {
            lampImageButton.setOnClickListener {
                Log.d(TAG, "Lamp is CLICKED!!!")

                // TODO: 램프 꺼지게/켜지게 & 배경 및 아이템의 조도 다 바꾸기
            }

            windowImageButton.setOnClickListener {
                Log.d(TAG, "Window is CLICKED!!!")

                // TODO: 창문 닫히게/열리게 & 배경 및 아이템의 조도 다 바꾸기
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
}