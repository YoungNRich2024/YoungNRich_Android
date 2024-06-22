package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.youngnrich.android.databinding.FragmentWallTwoBinding
import com.youngnrich.android.viewmodels.SecondRoomGameViewModel

private const val TAG = "Wall-2 Fragment"

class WallTwoFragment : Fragment() {
    private var _binding: FragmentWallTwoBinding? = null
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
        Log.d(TAG, "you're now in Wall 2")

        _binding = FragmentWallTwoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 투자 성향 테스트를 완료했다면 액자 이미지를 '눈 뜬' 버전으로 교체하기
        sharedSecondRoomGameViewModel.isInvestmentBehaviorTestCompleteLiveData.observe(viewLifecycleOwner) { isInvestmentBehaviorTestCompleteLiveData ->
            if (isInvestmentBehaviorTestCompleteLiveData) {
                binding.figuresFrameImageButton.setImageResource(R.drawable.figures_frame_eyes_open_bright)
            }
        }

        binding.apply {
            vaultImageButton.setOnClickListener {
                Log.d(TAG, "Vault is CLICKED!!!")

                // TODO: 퍼즐 3개가 모두 solved 되었을 때만 금고 문이 열리는 그림으로 교체 & 다시 클릭하면 다음 퍼즐 5 방으로 이동함
            }

            figuresFrameImageButton.setOnClickListener {
                Log.d(TAG, "Figures Frame area is CLICKED!!!")

                // TODO: 클릭하면 액자를 확대한 fragment 띄우기
                if (sharedSecondRoomGameViewModel.isInvestmentBehaviorTestComplete) {
                    // 만약 투자 성향 테스트를 완료했다면 (액자의 눈들이 떠져있는 상태)
                    // Figure Frames fragment 띄우기
                    secondRoomGameActivity.openFragment(FigureFramesFragment.newInstance())
                } else {
                    // 만약 투자 성향 테스트를 완료하지 않았다면 (액자의 눈들이 여전히 감겨있는 상태)
                    // 좌우로 흔들리는 모션만
                    rattlingFiguresFrame()
                }
            }

            investmentBehaviorTestImageButton.setOnClickListener {
                Log.d(TAG, "Investment Behavior Test area is CLICKED!!!")

                // 클릭하면 투자 성향 테스트 fragment 띄우기
                secondRoomGameActivity.openFragment(InvestmentBehaviorTestFragment.newInstance())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "onDestroyView(), Wall-2")

        _binding = null
    }

    private fun rattlingFiguresFrame() {
        val rattlingAnimation = AnimationUtils.loadAnimation(context, R.anim.rattle_of_figures_frame)
        binding.figuresFrameImageButton.startAnimation(rattlingAnimation)
    }
}