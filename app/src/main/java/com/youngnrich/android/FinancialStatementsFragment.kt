package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youngnrich.android.databinding.FragmentFinancialStatementsBinding

private const val TAG = "FinancialStatementsFragment"

class FinancialStatementsFragment : Fragment() {

    companion object {
        fun newInstance() = FinancialStatementsFragment()
    }

    private var _binding: FragmentFinancialStatementsBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val secondRoomGameActivity: SecondRoomGameActivity
        get() = checkNotNull(activity as? SecondRoomGameActivity) {
            "Cannot access secondRoomGameActivity because it is null."
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "$TAG is open")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinancialStatementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            financialStatementsBaseNavigation.apply {
                baseArrowLeft.visibility = View.GONE
                baseArrowRight.visibility = View.GONE

                baseArrowBottom.setOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            financialStatementAImageButton.setOnClickListener {
                secondRoomGameActivity.openFragment(ZoomedFinancialStatementFragment.newInstance("A"))
            }

            financialStatementBImageButton.setOnClickListener {
                secondRoomGameActivity.openFragment(ZoomedFinancialStatementFragment.newInstance("B"))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}