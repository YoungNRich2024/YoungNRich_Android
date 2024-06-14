package com.youngnrich.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youngnrich.android.databinding.FragmentTvBinding

private const val TAG = "TvFragment"

class TvFragment : Fragment() {
    // TODO 유튜브 영상 로드
    companion object {
        fun newInstance() = TvFragment()
    }

    private var _binding: FragmentTvBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val youtubeVideo = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/sv91cHVPBVs?si=W5rCncfL7ihohM5v\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(inflater, container, false)
//        _baseBinding = ActivityBaseNavigationBinding.bind(binding.root)

//        binding.youtubeWebView.apply {
//            settings.javaScriptEnabled = true
//            webChromeClient = WebChromeClient()
//            loadData(youtubeVideo, "text/html", "utf-8")
//        }
//
//        return binding.root

//        val view = inflater.inflate(R.layout.fragment_tv, container, false)

        // Youtube -- WebView ver.
//        binding.youtubeWebView.apply {
//            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
//
//            webViewClient = object : WebViewClient() {
//                override fun onReceivedError(
//                    view: WebView?,
//                    request: WebResourceRequest?,
//                    error: WebResourceError?
//                ) {
//                    super.onReceivedError(view, request, error)
//                    // Handle the error
//                    Log.e(TAG, "webViewClient Error")
//                }
//            }
//            settings.apply {
//                javaScriptEnabled = true
//                domStorageEnabled = true
//                cacheMode = WebSettings.LOAD_DEFAULT
//                mediaPlaybackRequiresUserGesture = false
//                allowFileAccess = true
//                databaseEnabled = true
//                //loadWithOverviewMode = true
//                //useWideViewPort = true
//            }
//            //loadData(youtubeVideo, "text/html", "utf-8")
//            loadUrl(youtubeVideo)
//        }

        //todo
        viewLifecycleOwner.lifecycle.addObserver(binding.youtubePlayerView)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvBaseNavigation.apply {
                baseArrowLeft.visibility = View.GONE
                baseArrowRight.visibility = View.GONE

                baseArrowBottom.setOnClickListener {
                    // todo: 동영상 재생 하고 hosting activity 로 나가면 inventory 가 안 됨
                    binding.root.removeView(youtubePlayerView)

                    requireActivity().supportFragmentManager.popBackStack()

                    Log.d(TAG, "go back to hosting Activity, 'FirstRoomGameActivity'")
                }
            }


        }

    }

    // Youtube -- WebView ver.
//    override fun onPause() {
//        super.onPause()
//        binding.youtubeWebView.onPause()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        binding.youtubeWebView.onResume()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
//        binding.youtubeWebView.apply {
//            clearCache(true)
//            clearHistory()
//            destroy()
//        }

        _binding = null
    }
}