package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youngnrich.android.databinding.FragmentWallOneBinding

private const val TAG = "Wall-1 Fragment"

class WallOneFragment : Fragment() {

    private var _binding: FragmentWallOneBinding? = null
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

            storageClosetImageButton.setOnClickListener {
                Log.d(TAG, "Storage Closet is CLICKED!!!")

                // TODO: 만약 인벤토리에 수납장의 키가 존재한다면
                    // TODO: => 열린 수납장 그림으로 교체 & 다시 클릭하면 시험지(?)가 인벤토리에 수집되고 더 이상 클릭 안 되게 처리
                // TODO: 만약 인벤토리에 수납장의 키가 존재하지 않는다면
                    // TODO: 먼저 키가 필요하다는 듯한 다이얼로그 띄우기
            }

            radioTableImageButton.setOnClickListener {
                Log.d(TAG, "Radio is CLICKED!!!")

                // TODO: 라디오를 확대한 fragment 열기
            }

            financialStatementsImageButton.setOnClickListener {
                Log.d(TAG, "Financial Statements are CLICKED!!!")

                // TODO: 재무제표를 확대한 fragment 열기?
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "onDestroyView(), Wall-1")

        _binding = null
    }
}