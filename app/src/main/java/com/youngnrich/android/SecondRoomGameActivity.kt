package com.youngnrich.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.youngnrich.android.databinding.ActivitySecondRoomGameBinding

class SecondRoomGameActivity : AppCompatActivity() {

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, SecondRoomGameActivity::class.java)
        }
    }

    private lateinit var binding: ActivitySecondRoomGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondRoomGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.second_room_fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        binding.secondRoomBaseNavigation.apply {
            baseArrowBottom.visibility = View.GONE

            baseArrowLeft.setOnClickListener {
                val currentDestination = navController.currentDestination?.id
                val navigationAction = when (currentDestination) {
                    R.id.wallOneFragment -> R.id.action_wallOneFragment_to_wallFourFragment
                    R.id.wallTwoFragment -> R.id.action_wallTwoFragment_to_wallOneFragment
                    R.id.wallThreeFragment -> R.id.action_wallThreeFragment_to_wallTwoFragment
                    R.id.wallFourFragment -> R.id.action_wallFourFragment_to_wallThreeFragment
                    else -> throw IllegalStateException("Unexpected value: $currentDestination")
                }
                navController.navigate(navigationAction)
            }

            baseArrowRight.setOnClickListener {
                val currentDestination = navController.currentDestination?.id
                val navigationAction = when (currentDestination) {
                    R.id.wallOneFragment -> R.id.action_wallOneFragment_to_wallTwoFragment
                    R.id.wallTwoFragment -> R.id.action_wallTwoFragment_to_wallThreeFragment
                    R.id.wallThreeFragment -> R.id.action_wallThreeFragment_to_wallFourFragment
                    R.id.wallFourFragment -> R.id.action_wallFourFragment_to_wallOneFragment
                    else -> throw IllegalStateException("Unexpected value: $currentDestination")
                }
                navController.navigate(navigationAction)
            }
        }
    }
}