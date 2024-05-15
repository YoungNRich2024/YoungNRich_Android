package com.youngnrich.android

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.youngnrich.android.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    private val ynrViewModel: YNRViewModel by viewModels()

    private fun updateIntroUI() {
        // TextView UI 업데이트
        binding.introTextView.setText(ynrViewModel.currentIntro)

        // prevImageButton UI 업데이트
        if (ynrViewModel.isFirstIntro()) {
            binding.prevImageButton.visibility = View.INVISIBLE
        } else {
            binding.prevImageButton.visibility = View.VISIBLE
        }

        // (next/game)ImageButton UI 업데이트
        if (ynrViewModel.isLastIntro()) {
            binding.nextImageButton.visibility = View.INVISIBLE
            binding.gameImageButton.visibility = View.VISIBLE
        } else {
            binding.nextImageButton.visibility = View.VISIBLE
            binding.gameImageButton.visibility = View.INVISIBLE
        }
    }

    private fun startFirstRoomGameActivity() {
        val startFirstRoomGameActivityIntent = FirstRoomGameActivity.newIntent(this@IntroActivity)
        startActivity(startFirstRoomGameActivityIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        updateIntroUI()

        binding.prevImageButton.setOnClickListener {
            ynrViewModel.moveToPrevIntro()

            updateIntroUI()
        }

        binding.nextImageButton.setOnClickListener {
            ynrViewModel.moveToNextIntro()

            updateIntroUI()
        }

        binding.gameImageButton.setOnClickListener {
            startFirstRoomGameActivity()
        }

        binding.skipImageButton.setOnClickListener {
            startFirstRoomGameActivity()
        }
    }
}