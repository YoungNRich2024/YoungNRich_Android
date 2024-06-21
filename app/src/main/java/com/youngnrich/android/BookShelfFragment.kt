package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.youngnrich.android.databinding.FragmentBookShelfBinding
import com.youngnrich.android.viewmodels.SecondRoomGameViewModel

private const val TAG = "BookShelfFragment"

class BookShelfFragment : Fragment() {
    companion object {
        fun newInstance() = BookShelfFragment()
    }

    private var _binding: FragmentBookShelfBinding? = null
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
        _binding = FragmentBookShelfBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeRoomIllumination()

        binding.apply {
            bookshelfBaseNavigation.apply {
                baseArrowLeft.visibility = View.GONE
                baseArrowRight.visibility = View.GONE

                baseArrowBottom.setOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            blueBookImageButton.setOnClickListener {
                Log.d(TAG, "Blue Book is CLICKED!!!")

                // 파란 책을 볼 수 있는 fragment 열기
                secondRoomGameActivity.openFragment(BookFragment.newInstance("Blue"))
            }

            greenBookImageButton.setOnClickListener {
                Log.d(TAG, "Green Book is CLICKED!!!")

                // 초록 책을 볼 수 있는 fragment 열기
                secondRoomGameActivity.openFragment(BookFragment.newInstance("Green"))
            }

            q4BookImageButton.setOnClickListener {
                Log.d(TAG, "Q4 Book is CLICKED!!!")

                // TODO: Q4 책을 볼 수 있는 fragment 열기
            }

            redBookImageButton.setOnClickListener {
                Log.d(TAG, "Red Book is CLICKED!!!")

                // 빨간 책을 볼 수 있는 fragment 열기
                secondRoomGameActivity.openFragment(BookFragment.newInstance("Red"))
            }

            brownBookImageButton.setOnClickListener {
                Log.d(TAG, "Brown Book is CLICKED!!!")

                // TODO: 갈색 책을 볼 수 있는 fragment 열기
            }

            yellowBookImageButton.setOnClickListener {
                Log.d(TAG, "Yellow Book is CLICKED!!!")

                // 노란 책을 볼 수 있는 fragment 열기
                secondRoomGameActivity.openFragment(BookFragment.newInstance("Yellow"))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun changeRoomIllumination() {
        val lamp = sharedSecondRoomGameViewModel.isLampOn
        val window = sharedSecondRoomGameViewModel.isWindowOpen
        if (!lamp && !window) {
            binding.zoomedBookShelfImageView.setImageResource(R.drawable.zoomed_bookshelf_dark)
        }
    }
}