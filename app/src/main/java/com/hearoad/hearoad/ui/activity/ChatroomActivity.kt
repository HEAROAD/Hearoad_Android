package com.hearoad.hearoad.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hearoad.hearoad.R
import com.hearoad.hearoad.databinding.ActivityChatroomBinding
import com.hearoad.hearoad.ui.adapter.ChatroomAdapter
import com.hearoad.hearoad.ui.viewmodel.ChatroomViewModel
import com.hearoad.hearoad.utils.GlobalApplication

class ChatroomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatroomBinding
    private val viewModel: ChatroomViewModel by viewModels()
    private lateinit var chatroomAdapter: ChatroomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatroomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 전달된 Intent의 모든 데이터를 Map 형태로 받아서 처리
        val selectedGuides = mutableListOf<String>()
        val extras = intent.extras

        // 전달된 모든 key-value 쌍을 탐색
        extras?.keySet()?.forEach { key ->
            if (key.startsWith("selected_guide")) {  // 'selected_guide'로 시작하는 모든 키 처리
                val guideValue = extras.getString(key) ?: ""
                selectedGuides.add(guideValue)  // 값이 없을 경우 공백으로 처리
            }
        }

        // 선택된 가이드가 있을 경우 메시지 생성
        val guideMessage = if (selectedGuides.isNotEmpty()) {
            "Selected Guides: ${selectedGuides.joinToString(", ")}"
        } else {
            "No guides selected"
        }

        // Log로 받은 데이터를 확인
        Log.d("ChatroomActivity", "Received guides: ${selectedGuides.joinToString(", ")}")

        // 선택된 가이드를 UI에 표시하거나 활용
        Toast.makeText(this, guideMessage, Toast.LENGTH_SHORT).show()

        // Toast 띄운 후 변수 초기화
        selectedGuides.clear()


        val roomId = intent.getStringExtra("roomId") ?: return
        val token = GlobalApplication.spf.accessToken!!

        setupRecyclerView()

        // 메시지 및 제목 관찰자 설정
        viewModel.getChatRoomMessages(roomId, token)
        viewModel.messages.observe(this, Observer { messages ->
            chatroomAdapter.submitList(messages)
        })
        viewModel.title.observe(this, Observer { title ->
            supportActionBar?.title = title
        })

        // 촬영 버튼 클릭 시 비디오 촬영
        binding.btnChatroomCamera.apply {
            bringToFront() // 이미지뷰를 가장 앞에 위치시킵니다.
            setOnClickListener {
                val videoCaptureIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                if (videoCaptureIntent.resolveActivity(packageManager) != null) {
                    startActivityForResult(videoCaptureIntent, REQUEST_VIDEO_CAPTURE)
                } else {
                    Toast.makeText(this@ChatroomActivity, "카메라 앱을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 메시지 전송 버튼 클릭 시
        binding.btnChatroomSend.setOnClickListener {
            val messageText = binding.etChatroomInput.text.toString()
            if (!TextUtils.isEmpty(messageText)) {
                viewModel.sendMessage(roomId, token, messageText)
                binding.etChatroomInput.text.clear()
            } else {
                Toast.makeText(this, "메시지를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 비디오 촬영 후 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            val videoUri: Uri? = data?.data
            videoUri?.let {
                val intent = Intent(this, ChatcameraActivity::class.java).apply {
                    putExtra("videoUri", it.toString())
                }
                startActivity(intent)
            }
        } else {
            Log.e("ChatroomActivity", "Video capture failed or cancelled")
        }
    }

    companion object {
        const val REQUEST_VIDEO_CAPTURE = 1
    }

    private fun setupRecyclerView() {
        chatroomAdapter = ChatroomAdapter()
        binding.rvChatroom.apply {
            layoutManager = LinearLayoutManager(this@ChatroomActivity)
            adapter = chatroomAdapter
        }
    }
}
