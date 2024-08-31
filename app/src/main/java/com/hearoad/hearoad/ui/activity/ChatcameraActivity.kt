package com.hearoad.hearoad.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.hearoad.hearoad.databinding.ActivityChatcameraBinding
//import com.hearoad.hearoad.ui.viewmodel.HandLandmarkerHelper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ChatcameraActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "Hand Landmarker"
    }

    private lateinit var binding: ActivityChatcameraBinding
//    private lateinit var handLandmarkerHelper: HandLandmarkerHelper
//    private var preview: Preview? = null
//    private var imageAnalyzer: ImageAnalysis? = null
//    private var camera: Camera? = null
//    private var cameraProvider: ProcessCameraProvider? = null
//    private var cameraFacing = CameraSelector.LENS_FACING_FRONT
//
//    private lateinit var backgroundExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatcameraBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}