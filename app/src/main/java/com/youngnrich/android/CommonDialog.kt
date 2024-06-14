package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.youngnrich.android.databinding.DialogCommonBinding

private const val TAG = "CommonDialog"

class CommonDialog: DialogFragment {
    private var dialogText: Int? = null
    private var dialogTexts: List<Int>? = null

    private var dialogTextsIndex = 0

    constructor(dialogText: Int) {
        this.dialogText = dialogText
    }

    constructor(dialogTexts: List<Int>) {
        this.dialogTexts = dialogTexts
    }

    private lateinit var binding: DialogCommonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCommonBinding.inflate(inflater, container, false)

        val window = dialog?.window
        window?.setBackgroundDrawableResource(R.color.transparency)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window?.attributes)
        layoutParams.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        layoutParams.y = 28 // 하단 여백
        window?.attributes = layoutParams

        binding.apply {
            closeDialogButton.setOnClickListener {
                dismiss()
            }

            if (dialogText != null) {
                Log.d(TAG, "Single-line Dialog is gonna be opened")

                nextDialogImageButton.visibility = View.INVISIBLE
                closeDialogFrameLayout.visibility = View.VISIBLE
                commonDialogTextView.setText(dialogText!!)
            }

            if (dialogTexts != null) {
                Log.d(TAG, "Multi-line Dialog is gonna be opened")

                // next 버튼 활성화 & close 버튼 비활성화
                nextDialogImageButton.visibility = View.VISIBLE
                closeDialogFrameLayout.visibility = View.INVISIBLE
                commonDialogTextView.setText(dialogTexts!![dialogTextsIndex])

                nextDialogImageButton.setOnClickListener {
                    // next 버튼 눌렀을 때 다이얼로그의 다음 텍스트 나타나게 & 다이얼로그의 마지막에서는 next 버튼 비활성화 & close 버튼 활성화

                    dialogTextsIndex++

                    if (dialogTextsIndex == dialogTexts?.lastIndex) {
                        nextDialogImageButton.visibility = View.INVISIBLE
                        closeDialogFrameLayout.visibility = View.VISIBLE
                    }

                    commonDialogTextView.setText(dialogTexts!![dialogTextsIndex])
                }
            }
        }

        return binding.root
    }
}