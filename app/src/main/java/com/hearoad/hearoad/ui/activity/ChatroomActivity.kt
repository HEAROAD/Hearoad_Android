package com.hearoad.hearoad.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hearoad.hearoad.R
import com.hearoad.hearoad.databinding.ActivityChatroomBinding
import com.hearoad.hearoad.ui.adapter.ChatroomAdapter
import com.hearoad.hearoad.ui.viewmodel.ChatroomViewModel
import com.hearoad.hearoad.utils.GlobalApplication
import kotlin.math.log

class ChatroomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatroomBinding
    private val viewModel: ChatroomViewModel by viewModels()
    private lateinit var chatroomAdapter: ChatroomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatroomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val roomId = intent.getStringExtra("roomId") ?: return
        val token = GlobalApplication.spf.accessToken!!
        setupRecyclerView()

        viewModel.getChatRoomMessages(roomId, token)
        viewModel.messages.observe(this, Observer { messages ->
            chatroomAdapter.submitList(messages)
        })
        viewModel.title.observe(this, Observer { title ->
            supportActionBar?.title = title
        })

        // 촬영 버튼
        binding.btnChatroomCamera.setOnClickListener {
            Log.d("test camera", "카메라안됨")
            Toast.makeText(this, "Camera button clicked", Toast.LENGTH_SHORT).show()
            val videoCaptureIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(videoCaptureIntent, REQUEST_VIDEO_CAPTURE)
       }
        binding.btnChatroomSend.setOnClickListener {
            val messageText = binding.etChatroomInput.text.toString()
            if (!TextUtils.isEmpty(messageText)) {
                viewModel.sendMessage(roomId, token, messageText)
                binding.etChatroomInput.text.clear()
            }
        }

    }

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