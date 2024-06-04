package com.youngnrich.android

import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView

data class Slot(
    val slotNumber: Int,
    val slotFrameLayout: FrameLayout,
    val slotImageButton: ImageButton,
    val slotImageView: ImageView,
)
