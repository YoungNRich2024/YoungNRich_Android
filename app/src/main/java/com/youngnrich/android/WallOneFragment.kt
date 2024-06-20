package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.youngnrich.android.databinding.FragmentWallOneBinding
import com.youngnrich.android.viewmodels.SecondRoomGameViewModel

private const val TAG = "Wall-1 Fragment"

class WallOneFragment : Fragment() {

    private var _binding: FragmentWallOneBinding? = null
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
        Log.d(TAG, "you're now in Wall 1")

        _binding = FragmentWallOneBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            bookshelfImageButton.setOnClickListener {
                Log.d(TAG, "Bookshelf is CLICKED!!!")

                // TODO: 책장의 위 2칸 확대한 fragment 열기
            }

            if (sharedSecondRoomGameViewModel.isStorageClosetOpened) {
                storageClosetImageButton.setImageResource(R.drawable.storage_closet_open_bright)
            }
            storageClosetImageButton.setOnClickListener {
                Log.d(TAG, "Storage Closet is CLICKED!!!")

                val necessaryItem = GameItem.KEY_FOR_STORAGE_CLOSET
                if (!sharedSecondRoomGameViewModel.inventoryItems.contains(necessaryItem)) {
                    if (sharedSecondRoomGameViewModel.isStorageClosetOpened) {
                        // 이미 열고 난 후일 경우
                        Log.d(TAG, "Storage Closet is gonna be zoomed")

                        val isPuzzle2Solved = sharedSecondRoomGameViewModel.isPuzzle2Solved
                        Log.d(TAG, "Have you ever Solved Puzzle 2?: $isPuzzle2Solved")
                        if (isPuzzle2Solved) {
                            // 이미 퍼즐 2를 solved 했었다면 => solved 버전의 fragment 열기 (더 이상 수납장의 스위치는 조작 불가)
                            secondRoomGameActivity.openFragment(SolvedStorageClosetFragment.newInstance())
                        } else {
                            // 퍼즐 2를 solved 한 적이 없다면 => 일반적인 StorageClosetFragment 열기
                            secondRoomGameActivity.openFragment(StorageClosetFragment.newInstance())
                        }
                    } else {
                        // 한 번도 연 적이 없는 경우
                        Log.d(TAG, "NO key for storage closet!!!! Take it FIRST")
                        // 수납장 덜그럭 애니메이션
                        rattlingStorageCloset()
                    }
                } else {
                    // 인벤토리 안의 Key 클릭 후 곧바로 storageClosetImageButton 을 클릭했다면
                    if (GameItem.KEY_FOR_STORAGE_CLOSET.isSelected) {
                        Log.d(TAG, "YES key!!!! Storage Closet is UNLOCKED!!!")

                        // '열린' 수납장 이미지로 교체
                        storageClosetImageButton.setImageResource(R.drawable.storage_closet_open_bright)

                        // 인벤토리에서 Key 제거
                        secondRoomGameActivity.removeItemFromInventory(GameItem.KEY_FOR_STORAGE_CLOSET)

                        GameItem.KEY_FOR_STORAGE_CLOSET.isSelected = false

                        sharedSecondRoomGameViewModel.isStorageClosetOpened = true

                        secondRoomGameActivity.inactivateSlotButtonUI()
                    } else {
                        Log.d(TAG, "WITH the Key in Inventory, please!!!")

                        // 수납장 덜그럭 애니메이션
                        rattlingStorageCloset()
                    }
                }
            }

            radioTableImageButton.setOnClickListener {
                Log.d(TAG, "Radio is CLICKED!!!")

                // TODO: 라디오를 확대한 fragment 열기
            }

            val isFinancialStatementsCollected = sharedSecondRoomGameViewModel.isFinancialStatementsCollected
            if (isFinancialStatementsCollected)
                financialStatementsImageButton.visibility = View.GONE
            else {
                financialStatementsImageButton.setOnClickListener {
                    Log.d(TAG, "Financial Statements are CLICKED!!!")

                    // 재무제표에 대한 내용이 담긴 다이얼로그 띄우기
                    secondRoomGameActivity.showCommonDialog(GameItem.FINANCIAL_STATEMENTS)

                    // 인벤토리에 재무제표 넣기
                    secondRoomGameActivity.putItemIntoInventory(GameItem.FINANCIAL_STATEMENTS)

                    financialStatementsImageButton.visibility = View.GONE
                    sharedSecondRoomGameViewModel.isFinancialStatementsCollected = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "onDestroyView(), Wall-1")

        _binding = null
    }

    private fun rattlingStorageCloset() {
        val rattlingAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rattle_of_storage_closet)
        binding.storageClosetImageButton.startAnimation(rattlingAnimation)
    }

}