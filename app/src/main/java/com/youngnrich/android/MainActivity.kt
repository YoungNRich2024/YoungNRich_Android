package com.youngnrich.android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.youngnrich.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var keepSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash Screen
        val splashScreen = installSplashScreen()
        setupSplashScreen(splashScreen)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun setupSplashScreen(splashScreen: SplashScreen) {
        // Replace this timer with your logic to load data on the splash screen.
        splashScreen.setKeepOnScreenCondition { keepSplash }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplash = false

            val startLoginActivityIntent = Intent(this, LoginActivity::class.java)
            startActivity(startLoginActivityIntent)
        }, SPLASH_DELAY)
    }

    companion object {
        private const val SPLASH_DELAY = 1200L
    }
}