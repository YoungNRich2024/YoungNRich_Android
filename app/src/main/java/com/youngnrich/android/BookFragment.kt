package com.youngnrich.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youngnrich.android.databinding.FragmentBookBinding

private const val TAG = "BookFragment"

private const val WHICH_COLOR_BOOK = "whichColorBook"

class BookFragment : Fragment() {
    companion object {
        fun newInstance(colorBook: String): BookFragment {
            val fragment = BookFragment()
            val args = Bundle()
            args.putString(WHICH_COLOR_BOOK, colorBook)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentBookBinding? = null
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
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colorBookToShow = requireArguments().getString(WHICH_COLOR_BOOK)
        var isBookOpen = false

        binding.apply {
            bookBaseNavigation.apply {
                baseArrowLeft.visibility = View.GONE
                baseArrowRight.visibility = View.GONE

                baseArrowBottom.setOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            when (colorBookToShow) {
                "Blue" -> {
                    bookImageButton.apply {
                        setImageResource(R.drawable.blue_book_cover)
                        setOnClickListener {
                            if (isBookOpen) {
                                setImageResource(R.drawable.blue_book_cover)
                            } else {
                                setImageResource(R.drawable.blue_book_content)
                            }
                            isBookOpen = !isBookOpen
                        }
                    }
                }
                "Green" -> {
                    bookImageButton.apply {
                        setImageResource(R.drawable.green_book_cover)
                        setOnClickListener {
                            if (isBookOpen) {
                                setImageResource(R.drawable.green_book_cover)
                            } else {
                                setImageResource(R.drawable.green_book_content)
                            }
                            isBookOpen = !isBookOpen
                        }
                    }
                }
                "Red" -> {
                    bookImageButton.apply {
                        setImageResource(R.drawable.red_book_cover)
                        setOnClickListener {
                            if (isBookOpen) {
                                setImageResource(R.drawable.red_book_cover)
                            } else {
                                setImageResource(R.drawable.red_book_content)
                            }
                            isBookOpen = !isBookOpen
                        }
                    }
                }
                "Yellow" -> {
                    bookImageButton.apply {
                        setImageResource(R.drawable.yellow_book_cover)
                        setOnClickListener {
                            if (isBookOpen) {
                                setImageResource(R.drawable.yellow_book_cover)
                            } else {
                                setImageResource(R.drawable.yellow_book_content)
                            }
                            isBookOpen = !isBookOpen
                        }
                    }
                }
                else -> throw IllegalArgumentException("[ERROR] colorBookToShow -- WRONG arguments from BookShelfFragment")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}