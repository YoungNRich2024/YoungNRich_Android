package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.youngnrich.android.databinding.FragmentWallThreeBinding
import com.youngnrich.android.viewmodels.SecondRoomGameViewModel

private const val TAG = "Wall-3 Fragment"

class WallThreeFragment : Fragment() {
    private var _binding: FragmentWallThreeBinding? = null
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
        Log.d(TAG, "you're now in Wall 3")

        _binding = FragmentWallThreeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeRoomIllumination()

        binding.apply {
            val isKeyCollected = sharedSecondRoomGameViewModel.isKeyCollected
            if (isKeyCollected)
                coatImageButton.visibility = View.GONE
            else {
                coatImageButton.setOnClickListener {
                    Log.d(TAG, "Coat is CLICKED!!!")

                    // 코트 안의 열쇠에 대한 내용이 담긴 다이얼로그 띄우기
                    secondRoomGameActivity.showCommonDialog(GameItem.COAT)

                    // 인벤토리에 열쇠 넣기
                    secondRoomGameActivity.putItemIntoInventory(GameItem.KEY_FOR_STORAGE_CLOSET)

                    coatImageButton.isEnabled = false
                    sharedSecondRoomGameViewModel.isKeyCollected = true
                }
            }

            grandpaFrameImageButton.setOnClickListener {
                Log.d(TAG, "Grandpa Frame is CLICKED!!!")

                // 할아버지 액자에 대한 내용이 담긴 다이얼로그 띄우기
                secondRoomGameActivity.showCommonDialog(GameItem.GRANDPA_FRAME)
            }

            grandpaFrameDetailImageButton.setOnClickListener {
                Log.d(TAG, "Grandpa Frame's Detail is CLICKED!!!")

                // 할아버지 액자의 세부에 대한 내용이 담긴 다이얼로그 띄우기
                secondRoomGameActivity.showCommonDialog(GameItem.GRANDPA_FRAME_DETAIL)
            }

            wallThreeDoorImageButton.setOnClickListener {
                Log.d(TAG, "Door between Room 1 and Room 2 is CLICKED!!!")

                // 문에 대한 내용이 담긴 다이얼로그 띄우기
                secondRoomGameActivity.showCommonDialog(GameItem.DOOR)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "onDestroyView(), Wall-3")

        _binding = null
    }

    private fun changeRoomIllumination() {
        val lamp = sharedSecondRoomGameViewModel.isLampOn
        val window = sharedSecondRoomGameViewModel.isWindowOpen
        binding.apply {
            if (lamp && window) {
                wallThreeSlightlyDimImageView.visibility = View.GONE
                wallThreeTooDimImageView.visibility = View.GONE
            } else if (lamp || window) {
                wallThreeSlightlyDimImageView.visibility = View.VISIBLE
                wallThreeTooDimImageView.visibility = View.GONE
            } else {
                wallThreeSlightlyDimImageView.visibility = View.GONE
                wallThreeTooDimImageView.visibility = View.VISIBLE
            }
        }
    }
}