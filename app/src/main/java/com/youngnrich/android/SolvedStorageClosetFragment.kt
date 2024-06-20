package com.youngnrich.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youngnrich.android.databinding.FragmentSolvedStorageClosetBinding

private const val TAG = "SolvedStorageClosetFragment"

class SolvedStorageClosetFragment : Fragment() {

    companion object {
        fun newInstance() = SolvedStorageClosetFragment()
    }

    private var _binding: FragmentSolvedStorageClosetBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSolvedStorageClosetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            solvedStorageClosetBaseNavigation.apply {
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