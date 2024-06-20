package com.youngnrich.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youngnrich.android.databinding.FragmentZoomedFinancialStatementBinding

private const val TAG = "ZoomedFinancialStatementFragment"

private const val WHICH_FINANCIAL_STATEMENT = "whichFinancialStatement"

class ZoomedFinancialStatementFragment : Fragment() {

    companion object {
        fun newInstance(financialStatement: String): ZoomedFinancialStatementFragment {
            val fragment = ZoomedFinancialStatementFragment()
            val args = Bundle()
            args.putString(WHICH_FINANCIAL_STATEMENT, financialStatement)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentZoomedFinancialStatementBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentZoomedFinancialStatementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val financialStatementToShow = requireArguments().getString(WHICH_FINANCIAL_STATEMENT)

        binding.apply {

            zoomedFinancialStatementBaseNavigation.apply {
                baseArrowLeft.visibility = View.GONE
                baseArrowRight.visibility = View.GONE

                baseArrowBottom.setOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            when (financialStatementToShow) {
                "A" -> zoomedFinancialStatementImageView.setImageResource(R.drawable.financial_statement_a)
                "B" -> zoomedFinancialStatementImageView.setImageResource(R.drawable.financial_statement_b)
                else -> throw IllegalArgumentException("[ERROR] financialStatementToShow -- WRONG arguments from FinancialStatementsFragment")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}