package com.youngnrich.android

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.bumptech.glide.Glide
import com.youngnrich.android.databinding.FragmentInvestmentBehaviorTestBinding
import com.youngnrich.android.viewmodels.InvestmentBehaviorTestViewModel
import com.youngnrich.android.viewmodels.SecondRoomGameViewModel
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import com.yuyakaido.android.cardstackview.SwipeableMethod

private const val TAG = "InvestmentBehaviorTestFragment"

class InvestmentBehaviorTestFragment : Fragment(), CardStackListener {
    companion object {
        fun newInstance() = InvestmentBehaviorTestFragment()
    }

    private var _binding: FragmentInvestmentBehaviorTestBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val secondRoomGameActivity: SecondRoomGameActivity
        get() = checkNotNull(activity as? SecondRoomGameActivity) {
            "Cannot access secondRoomGameActivity because it is null."
        }
    private val sharedSecondRoomGameViewModel: SecondRoomGameViewModel by activityViewModels()

    private val investmentBehaviorTestViewModel: InvestmentBehaviorTestViewModel by viewModels()

    private lateinit var cardStackView: CardStackView
    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "$TAG is open")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInvestmentBehaviorTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            investmentBehaviorTestBaseNavigation.apply {
                baseArrowLeft.visibility = View.GONE
                baseArrowRight.visibility = View.GONE

                baseArrowBottom.setOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()

                    // 투자 성향 테스트를 완료하고 처음 InvestmentBehaviorTestFragment 나갔을 때만 나가자마자 "어디선가 시선이 느껴진다"는 듯한 다이얼로그 띄우기
                    if (sharedSecondRoomGameViewModel.isInvestmentBehaviorTestComplete && sharedSecondRoomGameViewModel.investmentBehaviorTestCompleteTiming) {
                        secondRoomGameActivity.showCommonDialog(GameItem.FIGURES_FRAME)

                        sharedSecondRoomGameViewModel.investmentBehaviorTestCompleteTiming = false
                    }
                }
            }

            Log.d(TAG, "${sharedSecondRoomGameViewModel.isInvestmentBehaviorTestComplete}")
            if (!sharedSecondRoomGameViewModel.isInvestmentBehaviorTestComplete) {

                cardStackView = binding.investmentBehaviorTestCardStackView
                manager = CardStackLayoutManager(context, this@InvestmentBehaviorTestFragment)
                adapter = CardStackAdapter(createSpots())

                investmentBehaviorTestStartImageButton.setOnClickListener {
                    investmentBehaviorTestCoverConstraintLayout.visibility = View.INVISIBLE

                    setupCardStackView()
                    addAnswerButtons(investmentBehaviorTestViewModel.currentQuestionIndex)
                }
            } else {
                investmentBehaviorTestCoverConstraintLayout.visibility = View.INVISIBLE

                for (i in bulletinBoardContentConstraintLayout.childCount - 1 downTo 0) {
                    val child = bulletinBoardContentConstraintLayout.getChildAt(i)
                    if (child.id != investmentBehaviorTestResultConstraintLayout.id) {
                        bulletinBoardContentConstraintLayout.removeView(child)
                    }
                }

                showInvestmentBehaviorTestResult()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction?.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction?) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Log.d("CardStackView", "onCardAppeared")
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Log.d("CardStackView", "onCardDisappeared")
    }

    private fun createSpots(): List<Question> {
        val investmentBehaviorTestQuestions = ArrayList<Question>()
        investmentBehaviorTestQuestions.add(Question(drawableRes = R.drawable.investment_behavior_test_question_1))
        investmentBehaviorTestQuestions.add(Question(drawableRes = R.drawable.investment_behavior_test_question_2))
        investmentBehaviorTestQuestions.add(Question(drawableRes = R.drawable.investment_behavior_test_question_3))
        investmentBehaviorTestQuestions.add(Question(drawableRes = R.drawable.investment_behavior_test_question_4))
        investmentBehaviorTestQuestions.add(Question(drawableRes = R.drawable.investment_behavior_test_question_5))
        investmentBehaviorTestQuestions.add(Question(drawableRes = R.drawable.investment_behavior_test_question_6))
        investmentBehaviorTestQuestions.add(Question(drawableRes = R.drawable.investment_behavior_test_question_7))

        return investmentBehaviorTestQuestions
    }

    private fun setupCardStackView() {
        initialize()
    }

    private fun initialize() {
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(Direction.Left)
            .build()

        manager.apply {
            setStackFrom(StackFrom.None)
            setVisibleCount(3)
            setTranslationInterval(8.0f)
            setScaleInterval(0.95f)
            setSwipeThreshold(0.3f)
            setMaxDegree(20.0f)
            setDirections(Direction.HORIZONTAL)
            setCanScrollHorizontal(true)
            setCanScrollVertical(false)
            setSwipeableMethod(SwipeableMethod.Automatic)
            setOverlayInterpolator(LinearInterpolator())
            setSwipeAnimationSetting(setting)
        }
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun addAnswerButtons(questionNum: Int) {
        binding.investmentBehaviorTestAnswersLinearLayout.removeAllViews() // 이전 뷰 제거

        val allInvestmentBehaviorTestAnswers = investmentBehaviorTestViewModel.answers

        for (answer in allInvestmentBehaviorTestAnswers[questionNum]) {
            val buttonContext: Context = ContextThemeWrapper(context, R.style.investmentBehaviorTestAnswerAppCompatButton)
            val button = AppCompatButton(buttonContext).apply {
                setText(answer.textResId)
                setOnClickListener {
                    investmentBehaviorTestViewModel.scoreOfInvestmentBehaviorTest += answer.point
                    Log.d(TAG, "Score of Investment Behavior Test: ${investmentBehaviorTestViewModel.scoreOfInvestmentBehaviorTest}")

                    if (investmentBehaviorTestViewModel.currentQuestionIndex == InvestmentBehaviorTestViewModel.MAX_INVESTMENT_BEHAVIOR_TEST_QUESTION_SIZE) {
                        // 잠깐 결과 로딩 -> 투자 성향 테스트의 결과 보여주기
                        val bulletinBoardContentConstraintLayout = binding.bulletinBoardContentConstraintLayout
                        val loadingInvestmentBehaviorTestResultLinearLayout = binding.loadingInvestmentBehaviorTestResultLinearLayout
                        val investmentBehaviorTestResultConstraintLayout = binding.investmentBehaviorTestResultConstraintLayout

                        for (i in bulletinBoardContentConstraintLayout.childCount - 1 downTo 0) {
                            val child = bulletinBoardContentConstraintLayout.getChildAt(i)
                            if (child.id != loadingInvestmentBehaviorTestResultLinearLayout.id
                                && child.id != investmentBehaviorTestResultConstraintLayout.id) {
                                bulletinBoardContentConstraintLayout.removeView(child)
                            }
                        }

                        loadingInvestmentBehaviorTestResultLinearLayout.visibility = View.VISIBLE

                        Glide.with(this@InvestmentBehaviorTestFragment).load(R.raw.loading_investment_behavior_test_result).into(binding.loadingInvestmentBehaviorTestResultImageView)

                        // Wait for 2 seconds before transitioning
                        Handler(Looper.getMainLooper()).postDelayed({
                            bulletinBoardContentConstraintLayout.removeView(loadingInvestmentBehaviorTestResultLinearLayout)

                            sharedSecondRoomGameViewModel.apply {
                                investmentBehaviorTestResult = investmentBehaviorTestViewModel.getInvestmentBehaviorTestResult()

                                isInvestmentBehaviorTestComplete = true
                                Log.d(TAG, "Investment Behavior Test is COMPLETE?: ${sharedSecondRoomGameViewModel.isInvestmentBehaviorTestComplete}")

                                investmentBehaviorTestCompleteTiming = true
                            }
                            showInvestmentBehaviorTestResult()
                        }, 2000)
                    } else {
                        investmentBehaviorTestViewModel.currentQuestionIndex++
                        addAnswerButtons(investmentBehaviorTestViewModel.currentQuestionIndex)
                        binding.investmentBehaviorTestCardStackView.swipe()
                    }
                }
            }
            binding.investmentBehaviorTestAnswersLinearLayout.addView(button)
        }
    }

    private fun showInvestmentBehaviorTestResult() {
        val resultImageRes: Int
        val resultTitleText: Int
        val resultTitleColor: Int
        val resultContentText: Int

        when (sharedSecondRoomGameViewModel.investmentBehaviorTestResult) {
            1 -> {
                resultImageRes = R.drawable.investment_behavior_test_result_1_stable
                resultTitleText = R.string.investment_behavior_test_result_title_1_stable
                resultTitleColor = ContextCompat.getColor(requireContext(), R.color.blue_book)
                resultContentText = R.string.investment_behavior_test_result_content_1_stable
            }
            2 -> {
                resultImageRes = R.drawable.investment_behavior_test_result_2_stability_seeking
                resultTitleText = R.string.investment_behavior_test_result_title_2_stability_seeking
                resultTitleColor = ContextCompat.getColor(requireContext(), R.color.green_book)
                resultContentText = R.string.investment_behavior_test_result_content_2_stability_seeking
            }
            3 -> {
                resultImageRes = R.drawable.investment_behavior_test_result_3_risk_neutral
                resultTitleText = R.string.investment_behavior_test_result_title_3_risk_neutral
                resultTitleColor = ContextCompat.getColor(requireContext(), R.color.yellow_book)
                resultContentText = R.string.investment_behavior_test_result_content_3_risk_neutral
            }
            4 -> {
                resultImageRes = R.drawable.investment_behavior_test_result_4_active_investor
                resultTitleText = R.string.investment_behavior_test_result_title_4_active_investor
                resultTitleColor = ContextCompat.getColor(requireContext(), R.color.brown_book)
                resultContentText = R.string.investment_behavior_test_result_content_4_active_investor
            }
            5 -> {
                resultImageRes = R.drawable.investment_behavior_test_result_5_aggresive_investor
                resultTitleText = R.string.investment_behavior_test_result_title_5_aggressive_investor
                resultTitleColor = ContextCompat.getColor(requireContext(), R.color.red_book)
                resultContentText = R.string.investment_behavior_test_result_content_5_aggressive_investor
            }
            else -> throw throw IllegalArgumentException("[ERROR] getInvestmentBehaviorTestResult() -- WRONG scoreOfInvestmentBehaviorTest")
        }

        binding.apply {
            investmentBehaviorTestResultImageView.setImageResource(resultImageRes)
            investmentBehaviorTestResultTitleTextView.apply {
                setText(resultTitleText)
                setTextColor(resultTitleColor)
            }
            investmentBehaviorTestResultContentTextView.setText(resultContentText)

            investmentBehaviorTestResultConstraintLayout.visibility = View.VISIBLE
        }
    }
}