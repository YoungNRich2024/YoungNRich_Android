package com.youngnrich.android

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youngnrich.android.databinding.FragmentRadioBinding

private const val TAG = "RadioFragment"

class RadioFragment : Fragment() {

    companion object {
        fun newInstance() = RadioFragment()
    }

    private var _binding: FragmentRadioBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRadioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            radioBaseNavigation.apply {
                baseArrowLeft.visibility = View.GONE
                baseArrowRight.visibility = View.GONE

                baseArrowBottom.setOnClickListener {
                    stopPuzzle3Audio()
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            radioPlayButton.setOnClickListener {
                Log.d(TAG, "Play Button is CLICKED!!!")

                // 음원 재생 시키기 => 훼이크 음원
                playPuzzle3Audio(R.raw.puzzle_3_radio_audio_reversed)
            }

            radioStopButton.setOnClickListener {
                Log.d(TAG, "Stop Button is CLICKED!!!")

                // 음원 멈추기
                stopPuzzle3Audio()
            }

            radioBackWindButton.setOnClickListener {
                Log.d(TAG, "Back Wind Button is CLICKED!!!")

                // 음원 역재생 시키기 => 정상 음원
                playPuzzle3Audio(R.raw.puzzle_3_radio_audio)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
        stopPuzzle3Audio()
    }

    private fun playPuzzle3Audio(audioResId: Int) {
        stopPuzzle3Audio()
        mediaPlayer = MediaPlayer.create(context, audioResId)
        mediaPlayer?.start()
    }

    private fun stopPuzzle3Audio() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
            mediaPlayer = null
        }
    }
}