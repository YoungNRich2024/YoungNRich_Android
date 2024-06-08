package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youngnrich.android.databinding.FragmentWallTwoBinding

private const val TAG = "Wall-2 Fragment"

class WallTwoFragment : Fragment() {
    private var _binding: FragmentWallTwoBinding? = null
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
        Log.d(TAG, "you're now in Wall 2")

        _binding = FragmentWallTwoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vaultImageButton.setOnClickListener {
                Log.d(TAG, "Vault is CLICKED!!!")

                // TODO: 퍼즐 3개가 모두 solved 되었을 때만 금고 문이 열리는 그림으로 교체 & 다시 클릭하면 다음 퍼즐 5 방으로 이동함
            }

            figuresFrameImageButton.setOnClickListener {
                Log.d(TAG, "Figures Frame area is CLICKED!!!")

                // TODO: 클릭하면 액자를 확대한 fragment 띄우기
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "onDestroyView(), Wall-2")

        _binding = null
    }
}