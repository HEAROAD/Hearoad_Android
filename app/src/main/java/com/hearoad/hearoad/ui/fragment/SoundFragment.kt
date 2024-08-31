package com.hearoad.hearoad.ui.fragment

import android.content.Intent
import android.graphics.Rect
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hearoad.hearoad.R
import com.hearoad.hearoad.data.api.ApiService
import com.hearoad.hearoad.data.model.response.EmergencyVoiceResponse
import com.hearoad.hearoad.data.network.RetrofitClient
import com.hearoad.hearoad.databinding.FragmentSoundBinding
import com.hearoad.hearoad.ui.adapter.SoundAdapter
import com.hearoad.hearoad.utils.GlobalApplication
import kotlinx.coroutines.launch

class SoundFragment : Fragment() {

    private var _binding: FragmentSoundBinding? = null
    private val binding get() = _binding!!
    private lateinit var soundAdapter: SoundAdapter
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSoundBinding.inflate(inflater, container, false)
        return binding.root


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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        fetchSoundData()
        setupListeners()

        binding.btnSoundAdd.setOnClickListener {
            addSound()
        }

    }

    private fun setupRecyclerView() {
        soundAdapter = SoundAdapter { filePath ->
            playSound(filePath)
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = soundAdapter
            addItemDecoration(SpaceItemDecoration(18))
        }
    }
    private fun setupListeners() {
        binding.ivSound1.setOnClickListener {
            playSound(binding.ivSound1.tag.toString())
        }

        binding.ivSound2.setOnClickListener {
            playSound(binding.ivSound2.tag.toString())
        }

        binding.ivSound3.setOnClickListener {
            playSound(binding.ivSound3.tag.toString())
        }
    }

    private fun fetchSoundData() {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val token = "Bearer " + GlobalApplication.spf.accessToken

        lifecycleScope.launch {
            try {
                val response = apiService.getEmergencyVoices(token)
                if (response.isSuccessful) {
                    val soundList = response.body() ?: emptyList()
                    if (soundList.size >= 3) {
                        bindFirstThreeSounds(soundList)
                        soundAdapter.submitList(soundList.subList(3, soundList.size))
                    } else {
                        Toast.makeText(context, "데이터가 충분하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "서버 응답에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bindFirstThreeSounds(soundList: List<EmergencyVoiceResponse>) {
        val firstSound = soundList[0]
        val secondSound = soundList[1]
        val thirdSound = soundList[2]

        binding.ivSound1.tag = firstSound.filePath
        binding.ivSound2.tag = secondSound.filePath
        binding.ivSound3.tag = thirdSound.filePath

    }
    private fun playSound(url: String) {
        mediaPlayer?.release() // 이전에 재생 중인 미디어 플레이어를 해제

        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepare()
            start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mediaPlayer?.release() // 미디어 플레이어 해제
        mediaPlayer = null
    }

    private fun addSound() {
        val addSoundBottomSheet = AddSoundBottomSheet()
        addSoundBottomSheet.show(parentFragmentManager, AddSoundBottomSheet.TAG)
    }


}

class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space

    }
}