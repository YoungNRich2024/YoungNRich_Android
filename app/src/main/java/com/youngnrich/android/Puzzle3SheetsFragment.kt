package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youngnrich.android.databinding.FragmentPuzzle3SheetsBinding

private const val TAG = "Puzzle3SheetsFragment"

class Puzzle3SheetsFragment : Fragment() {

    companion object {
        fun newInstance() = Puzzle3SheetsFragment()
    }

    private var _binding: FragmentPuzzle3SheetsBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "$TAG is open")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPuzzle3SheetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            zoomedPuzzle3SheetsBaseNavigation.apply {
                baseArrowLeft.visibility = View.GONE
                baseArrowRight.visibility = View.GONE

                baseArrowBottom.setOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}