package com.youngnrich.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youngnrich.android.databinding.FragmentTvBinding

private const val TAG = "TvFragment"

class TvFragment : Fragment() {

    companion object {
        fun newInstance() = TvFragment()
    }

    private var _binding: FragmentTvBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(inflater, container, false)

        // 유튜브 영상 로드
        viewLifecycleOwner.lifecycle.addObserver(binding.youtubePlayerView)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvBaseNavigation.apply {
                baseArrowLeft.visibility = View.GONE
                baseArrowRight.visibility = View.GONE

                baseArrowBottom.setOnClickListener {
                    binding.root.removeView(youtubePlayerView)

                    requireActivity().supportFragmentManager.popBackStack()

                    Log.d(TAG, "go back to hosting Activity, 'FirstRoomGameActivity'")
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}