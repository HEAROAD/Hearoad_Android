package com.hearoad.hearoad.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hearoad.hearoad.R
import com.hearoad.hearoad.databinding.FragmentMypageBinding
import com.hearoad.hearoad.ui.activity.ChatcameraActivity
import com.hearoad.hearoad.ui.activity.LoginActivity
import com.hearoad.hearoad.ui.activity.MyvoiceActivity
import com.hearoad.hearoad.ui.viewmodel.MypageViewModel
import com.hearoad.hearoad.utils.GlobalApplication

class MypageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!
    private val mypageViewModel: MypageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 목소리 등록
        binding.btnMypageMyvoice.setOnClickListener {
            val intent = Intent(requireContext(), MyvoiceActivity::class.java)
            startActivity(intent)
        }

        // ViewModel을 통해 데이터 가져오기
        mypageViewModel.mypageData.observe(viewLifecycleOwner, Observer { mypageData ->
            binding.tvMypage.text = mypageData.character
            if (mypageData.character == "히로") {
                binding.ivMypage.setImageResource(R.drawable.ic_h)
            } else {
                binding.ivMypage.setImageResource(R.drawable.img_frog)
            }
        })


        // 데이터 로드
        val token = GlobalApplication.spf.accessToken
        if (token != null && token.isNotEmpty()) {
            mypageViewModel.fetchMypageData(token)
        } else {
            Toast.makeText(requireContext(), "토큰이 없습니다.", Toast.LENGTH_SHORT).show()
        }
        // 로그아웃
        binding.tvMypageLogout.setOnClickListener {
            logout()
        }
    }


    private fun logout() {
        GlobalApplication.spf.clearAuthToken()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}