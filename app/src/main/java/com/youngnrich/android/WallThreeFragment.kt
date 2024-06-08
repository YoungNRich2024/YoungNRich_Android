package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youngnrich.android.databinding.FragmentWallThreeBinding

private const val TAG = "Wall-3 Fragment"

class WallThreeFragment : Fragment() {
    private var _binding: FragmentWallThreeBinding? = null
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
        Log.d(TAG, "you're now in Wall 3")

        _binding = FragmentWallThreeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            grandpaFrameImageButton.setOnClickListener {
                Log.d(TAG, "Grandpa Frame is CLICKED!!!")

                // TODO: 할아버지 액자에 대한 내용이 담긴 다이얼로그 띄우기
            }

            wallThreeDoorImageButton.setOnClickListener {
                Log.d(TAG, "Door between Room 1 and Room 2 is CLICKED!!!")

                // TODO: 문에 대한 내용이 담긴 다이얼로그 띄우기
            }

            coatImageButton.setOnClickListener {
                Log.d(TAG, "Coat is CLICKED!!!")

                // TODO: 코트 안의 열쇠에 대한 내용이 담긴 다이얼로그 띄우기 & 인벤토리에 열쇠 넣기
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "onDestroyView(), Wall-3")

        _binding = null
    }
}