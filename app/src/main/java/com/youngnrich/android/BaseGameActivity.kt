package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "BaseGameActivity"

open class BaseGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "BaseGameActivity is onCreate()!!!")

        onBackPressedDispatcher.addCallback(this@BaseGameActivity, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showQuitDialog()
            }
        })
    }

    private fun showQuitDialog() {
        val quitDialog = QuitDialog()
        quitDialog.isCancelable = false
        quitDialog.show(this.supportFragmentManager, "QuitDialog")
    }

    private fun saveGameStateToServer(data: Map<String, Any>, callback: () -> Unit) {
        // TODO: 게임 중간 저장
        // TODO: 중간 저장이 완료된 후에는 HomeActivity 로 돌아갈 것
    }
}