package com.hearoad.hearoad.ui.fragment

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hearoad.hearoad.R
import com.hearoad.hearoad.databinding.FragmentSoundBinding
import com.hearoad.hearoad.ui.viewmodel.SoundViewModel

class SoundFragment : Fragment() {

    private var _binding: FragmentSoundBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSoundBinding.inflate(inflater, container, false)


        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        binding.btnSoundAdd.setOnClickListener {
            addSound()
        }

        //소리 클릭 시
//        button.setOnClickListener {
//            // 1. 버튼을 선택 상태로 변경
//            button.isSelected = true
//
//            // 2. 음성 재생 시작 (이 예에서는 MediaPlayer 사용)
//            val mediaPlayer = MediaPlayer.create(this, R.raw.sound_file)
//            mediaPlayer.start()
//
//            // 3. 음성 재생이 끝날 때 실행할 리스너
//            mediaPlayer.setOnCompletionListener {
//                // 음성 재생이 끝난 후, 버튼을 원래 상태로 되돌림
//                button.isSelected = false
//                mediaPlayer.release() // MediaPlayer 자원 해제
//            }
//        }


        return binding.root
    }

    private fun addSound() {
        val addSoundBottomSheet = AddSoundBottomSheet()
        addSoundBottomSheet.show(parentFragmentManager, AddSoundBottomSheet.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}