package com.youngnrich.android

enum class GameItem(
    val isInventoryItem: Boolean,
    val drawableResId: Int? = null,
    val isZoomable: Boolean = false,
    var isSelected: Boolean = false,
) {
    IPAD(true, R.drawable.ipad,true),
    REMOTE_CONTROLLER(true, R.drawable.remote_controller, false),
    PICTURE_FRAME(false),
    PILLOW(false),
    TELEVISION(false);
}