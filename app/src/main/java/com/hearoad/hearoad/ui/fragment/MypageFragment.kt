package com.hearoad.hearoad.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hearoad.hearoad.R
import com.hearoad.hearoad.databinding.FragmentMypageBinding
import com.hearoad.hearoad.ui.activity.MyvoiceActivity
import com.hearoad.hearoad.ui.viewmodel.MypageViewModel

class MypageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

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

        // 로그아웃
        binding.tvMypageLogout.setOnClickListener {
//            logout()
        }
    }

//    private fun logout() {
//        apiService.logout().enqueue(object : Callback<Void> {
//            override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(requireContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show()
//                    // 로그아웃 성공 시 추가로 필요한 작업이 있다면 여기에 추가
//                } else {
//                    Toast.makeText(requireContext(), "로그아웃 실패", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<Void>, t: Throwable) {
//                Toast.makeText(requireContext(), "로그아웃 에러: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}