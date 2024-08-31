package com.hearoad.hearoad.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hearoad.hearoad.R
import com.hearoad.hearoad.databinding.FragmentGuideBinding
import com.hearoad.hearoad.ui.activity.ChatroomActivity
import com.hearoad.hearoad.ui.activity.MyvoiceActivity


class GuideFragment : Fragment() {

    private var _binding : FragmentGuideBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clGuide1.setOnClickListener {
            val intent = Intent(requireContext(), ChatroomActivity::class.java)
            startActivity(intent)
        }
    }

}