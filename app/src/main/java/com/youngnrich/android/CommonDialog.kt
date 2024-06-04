package com.youngnrich.android

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.youngnrich.android.databinding.DialogCommonBinding

class CommonDialog: DialogFragment {
    private var dialogText: Int? = null
    private var dialogTexts: List<Int>? = null

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

        binding.closeDialogButton.setOnClickListener {
            dismiss()
        }

        if (dialogText != null) {
            binding.nextDialogImageButton.visibility = View.INVISIBLE
            binding.commonDialogTextView.setText(dialogText!!)
        }
        if (dialogTexts != null) {
            // TODO next 버튼 활성화 & next 버튼 눌렀을 때 다음 텍스트 나타나게 & 마지막 텍스트에서는 다시 next 버튼 비활성화
        }

        return binding.root
    }
}