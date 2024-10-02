package com.hearoad.hearoad.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hearoad.hearoad.databinding.FragmentGuideMap02Binding

class MapGuideFragment2 : Fragment() {

    private var _binding: FragmentGuideMap02Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 데이터 바인딩을 통해 XML 레이아웃 인플레이트
        _binding = FragmentGuideMap02Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



