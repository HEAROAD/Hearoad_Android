package com.hearoad.hearoad.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.hearoad.hearoad.R
import com.hearoad.hearoad.databinding.FragmentGuideBinding
import com.hearoad.hearoad.ui.activity.GuideActivity

class GuideFragment : Fragment() {

    private var _binding: FragmentGuideBinding? = null
    private val binding get() = _binding!!

    private var isImageChanged = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ImageView 클릭 이벤트 처리
        binding.btnGuideTranslate2.setOnClickListener {
            if (!isImageChanged) {
                binding.btnGuideTranslate2.setImageResource(R.drawable.img_translate) // 변경할 이미지
                isImageChanged = true
                binding.ivGuide2.visibility = View.GONE // 특정 ImageView를 숨김 처리
                playVideo()
            }
        }

        // 기존의 가이드 클릭 이벤트 처리
        binding.clGuide1.setOnClickListener {
            startGuideActivity()
        }
        binding.clGuide2.setOnClickListener {
            startGuideActivity()
        }
        binding.clGuide3.setOnClickListener {
            startGuideActivity()
        }
        binding.clGuide4.setOnClickListener {
            startGuideActivity()
        }
        binding.clGuide5.setOnClickListener {
            startGuideActivity()
        }
    }

    private fun startGuideActivity() {
        val intent = Intent(requireContext(), GuideActivity::class.java)
        startActivity(intent)
    }

    private fun playVideo() {
        val videoUri = Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.needhelp) // 영상 파일을 raw 폴더에 추가
        binding.videoView.apply {
            setVideoURI(videoUri)
            visibility = View.VISIBLE
            start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
