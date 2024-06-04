package com.youngnrich.android

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.youngnrich.android.GameItem.IPAD
import com.youngnrich.android.GameItem.PICTURE_FRAME
import com.youngnrich.android.GameItem.PILLOW
import com.youngnrich.android.GameItem.REMOTE_CONTROLLER
import com.youngnrich.android.GameItem.TELEVISION
import com.youngnrich.android.databinding.ActivityFirstRoomGameBinding

private const val TAG = "FirstRoomGameActivity"

class FirstRoomGameActivity : BaseGameActivity() {
    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, FirstRoomGameActivity::class.java)
        }
    }

    // TODO: Back Button/Gesture 을 하면 게임 종료 다이얼로그("게임을 종료하시겠습니까?")를 띄워, "예" 클릭 시 게임의 상태 저장 후 HomeActivity 로 이동시킬 것 (로딩 페이지처럼 gif 첨부)

    private lateinit var binding: ActivityFirstRoomGameBinding

    private val ynrViewModel: YNRViewModel by viewModels()

    private lateinit var slots: List<Slot>
    private var interactiveSlot: Slot? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirstRoomGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        slots = listOf(
            Slot(0, binding.firstRoomInventory.slot0, binding.firstRoomInventory.slot0ImageButton, binding.firstRoomInventory.slot0ImageView),
            Slot(1, binding.firstRoomInventory.slot1, binding.firstRoomInventory.slot1ImageButton, binding.firstRoomInventory.slot1ImageView),
            Slot(2, binding.firstRoomInventory.slot2, binding.firstRoomInventory.slot2ImageButton, binding.firstRoomInventory.slot2ImageView),
            Slot(3, binding.firstRoomInventory.slot3, binding.firstRoomInventory.slot3ImageButton, binding.firstRoomInventory.slot3ImageView),
            Slot(4, binding.firstRoomInventory.slot4, binding.firstRoomInventory.slot4ImageButton, binding.firstRoomInventory.slot4ImageView),
            Slot(5, binding.firstRoomInventory.slot5, binding.firstRoomInventory.slot5ImageButton, binding.firstRoomInventory.slot5ImageView),
        )

        slots.forEach { slot ->
            slot.apply {
                slotImageButton.setOnClickListener {
                    Log.d(TAG, "slotImageButton $slotNumber is CLICKED!!!")

                    if (hasItem(slot, IPAD)) {
                        Log.d(TAG, "Ipad in Inventory is CLICKED!!!")

                        IPAD.isSelected = true

                        activateSlotButtonUI(slot)
                    }

                    if (hasItem(slot, REMOTE_CONTROLLER)) {
                        Log.d(TAG, "Remote Controller in Inventory is CLICKED!!!")

                        REMOTE_CONTROLLER.isSelected = true

                        activateSlotButtonUI(slot)
                    }
                }
            }
        }

        binding.apply {

            televisionImageButton.setOnClickListener {
                Log.d(TAG, "TV is clicked!!!!!")

                val necessaryItem = REMOTE_CONTROLLER
                if (!ynrViewModel.inventoryItems.contains(necessaryItem)) {
                    Log.d(TAG, "NO remote controller!!!! Take it FIRST")

                    // 리모콘이 필요하다는 듯한 멘트가 담긴 다이얼로그 출력
                    showCommonDialog(TELEVISION)
                } else {
                    // 인벤토리 안의 Remote Controller 클릭 후 곧바로 televisionImageButton 을 클릭했다면 => TvFragment 열기
                    if (REMOTE_CONTROLLER.isSelected) {
                        Log.d(TAG, "YES remote controller!!!! TV is gonna turn ON -- open TvFragment")

                        REMOTE_CONTROLLER.isSelected = false

                        inactivateSlotButtonUI()

                        openFragment(TvFragment.newInstance())
                    } else {
                        Log.d(TAG, "WITH the Remote Controller in Inventory, please!!!")
                    }
                }
            }

            ipadImageButton.setOnClickListener {
                Log.d(TAG, "iPad is clicked!!!!!")

                val ipadItem = IPAD

                // 아이패드를 발견했다는 문구가 담긴 다이얼로그 출력
                showCommonDialog(ipadItem)

                // 인벤토리에 아이패드 담기
                putItemIntoInventory(ipadItem)

                // 더이상 터치 불가
                it.visibility = View.GONE
            }

            pillowImageView.setOnClickListener {
                Log.d(TAG, "Pillow is clicked!!!!!")

                // 리모콘을 발견했다는 문구가 담긴 다이얼로그 출력
                showCommonDialog(PILLOW)

                // 인벤토리에 리모콘 담기
                val remoteControllerItem = REMOTE_CONTROLLER
                putItemIntoInventory(remoteControllerItem)

                // 더이상 터치 불가
                it.isEnabled = false
            }

            pictureFrameImageView.setOnClickListener {
                Log.d(TAG, "Picture Frame is clicked!!!!!")

                // 액자와 관련된 멘트가 담긴 다이얼로그 출력
                showCommonDialog(PICTURE_FRAME)
            }

            firstRoomInventory.zoomImageButton.setOnClickListener {
                // 인벤토리 안의 Ipad 클릭 후 곧바로 zoomButton 을 클릭했다면 => IpadFragment 열기
                if (IPAD.isSelected) {
                    Log.d(TAG, "open IpadFragment")

                    openFragment(IpadFragment.newInstance())
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

        if (ev.action == MotionEvent.ACTION_DOWN) {
            val tvRect = Rect()
            val zoomRect = Rect()

            binding.apply {
                televisionImageButton.getGlobalVisibleRect(tvRect)
                firstRoomInventory.zoomImageButton.getGlobalVisibleRect(zoomRect)
            }

            // 인벤토리 안의 Remote Controller 클릭 후 televisionImageButton 이 아닌 다른 것을 클릭했다면 => Remote Controller 의 isSelected 를 false 로 바꾸기
            if (REMOTE_CONTROLLER.isSelected) {
                if (!tvRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    Log.d(TAG, "Remote Controller in Inventory is Cancelled!!!")

                    REMOTE_CONTROLLER.isSelected = false

                    inactivateSlotButtonUI()
                }
            }

            // 인벤토리 안의 Ipad 클릭 후 zoomButton 이 아닌 다른 것을 클릭했다면 => Ipad 의 isSelected 를 false 로 바꾸기
            if (IPAD.isSelected) {
                if (!zoomRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    Log.d(TAG, "Ipad in Inventory is Cancelled!!!")

                    IPAD.isSelected = false

                    inactivateSlotButtonUI()
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun activateSlotButtonUI(slot: Slot) {
        interactiveSlot = slot

        interactiveSlot.apply {
            this?.slotImageButton?.setImageResource(R.drawable.inventory_slot_selected)
            this?.slotFrameLayout?.apply {
                scaleX = 1.2f
                scaleY = 1.2f
            }
        }
    }

    private fun inactivateSlotButtonUI() {
        interactiveSlot.apply {
            this?.slotImageButton?.setImageResource(R.drawable.inventory_slot)
            this?.slotFrameLayout?.apply {
                scaleX = 1.0f
                scaleY = 1.0f
            }
        }

        interactiveSlot = null
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun hasItem(slot: Slot, item: GameItem): Boolean {
        return  if (slot.slotNumber < ynrViewModel.inventoryItems.size) {
            ynrViewModel.inventoryItems[slot.slotNumber] == item
        } else {
            false
        }
    }

    private fun chooseSlotToFill(slotNumber: Int): Slot {
        return slots.find { it.slotNumber == slotNumber }!!
    }

    private fun putItemIntoInventory(item: GameItem) {
        if (item.drawableResId != null) {
            val slotNumber = ynrViewModel.inventoryItems.size
            if (slotNumber < YNRViewModel.MAX_INVENTORY_SIZE) {
                val slot = chooseSlotToFill(slotNumber)
                slot.slotImageView.setImageResource(item.drawableResId)

                ynrViewModel.inventoryItems.add(item)
            } else {
                Log.e(TAG, "slot is FULL!!!")
            }
        } else {
            Log.e(TAG, "this is the item which has NO drawableResId!!!")
        }
    }

    private fun showCommonDialog(item: GameItem) {
        val dialogText = when (item) {
            IPAD -> R.string.dialog_ipad
            PICTURE_FRAME -> R.string.dialog_picture_frame
            PILLOW -> R.string.dialog_pillow
            TELEVISION -> R.string.dialog_television
            else -> Log.e(TAG, "[ERROR] showCommonDialog() -- WRONG item")
        }

        val commonDialog = CommonDialog(dialogText)
        commonDialog.isCancelable = false
        commonDialog.show(this.supportFragmentManager, "CommonDialog")
    }
}