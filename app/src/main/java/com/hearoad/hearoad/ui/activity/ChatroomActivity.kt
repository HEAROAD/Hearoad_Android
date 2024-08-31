package com.hearoad.hearoad.ui.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.hearoad.hearoad.R
import com.hearoad.hearoad.databinding.ActivityChatroomBinding

class ChatroomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatroomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatroomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 촬영 버튼
        binding.btnChatroomCamera.setOnClickListener {
            val intent = Intent(this, ChatcameraActivity::class.java)
            startActivity(intent)
       }
    }
}