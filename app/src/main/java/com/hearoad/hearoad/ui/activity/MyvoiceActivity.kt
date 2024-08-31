package com.hearoad.hearoad.ui.activity


import android.Manifest
import android.Manifest.permission.RECORD_AUDIO
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.hearoad.hearoad.databinding.ActivityMyvoiceBinding
import org.json.JSONObject
import java.io.IOException

class MyvoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyvoiceBinding
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var outputFilePath: String
    private var isRecording = false

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                toggleRecording()
            } else {
                Toast.makeText(this, "녹음 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMyvoice.setOnClickListener {
            requestPermissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
        }
        val responseString = intent.getStringExtra("response")
        if (responseString != null) {
            displayResult(responseString)
        }

    }
    private fun toggleRecording() {
        if (isRecording) {
            stopRecording()
        } else {
            startRecording()
        }
    }
    private fun startRecording() {
        outputFilePath = "${externalCacheDir?.absolutePath}/recording.mp3"
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFilePath)
            prepare()
            start()
        }
        binding.tvMyvoice.text = "녹음 중입니다.\n'가나다라마바사'를 5번 말하세요."

        binding.btnMyvoice.text = "완료하기"
        isRecording = true
    }

    private fun stopRecording() {
        try {
            mediaRecorder.apply {
                stop()
                release()
            }
            Toast.makeText(this, "녹음이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            goToNextPage()
            binding.btnMyvoice.text = "녹음하기"
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun goToNextPage() {
        val intent = Intent(this, RecordActivity::class.java)
        intent.putExtra("audioFilePath", outputFilePath)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::mediaRecorder.isInitialized) {
            mediaRecorder.release()
        }
    }

    private fun displayResult(responseString: String) {
        binding.tvMyvoice.text = "목소리 분석이 완료되었어요!"
        binding.clMyvoice.visibility = View.VISIBLE
        val jsonObject = JSONObject(responseString)
        val isSuccess = jsonObject.getBoolean("isSuccess")
        if (isSuccess) {
            val response = jsonObject.getJSONObject("response")
            val frequencyCategory = response.getString("frequencyCategory")
            val pitchCategory = response.getString("pitchCategory")
            val speedCategory = response.getString("speedCategory")
            val volumeCategory = response.getString("volumeCategory")
            val spectralCategory = response.getString("spectralCategory")
            val character = response.getString("character")

            binding.tvFrequency.text = "주파수: $frequencyCategory"
            binding.tvPitch.text = "음정: $pitchCategory"
            binding.tvSpeed.text = "속도: $speedCategory"
            binding.tvVolume.text = "볼륨: $volumeCategory"
            binding.tvSpectral.text = "스펙트럼: $spectralCategory"
            binding.tvCharacter.text = "캐릭터: $character"
        } else {
            binding.tvMyvoice.text = "분석에 실패했어요"
        }
    }
}
//class MyvoiceActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMyvoiceBinding
//
//    private lateinit var speechRecognizer: SpeechRecognizer
//    private var matchCount = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMyvoiceBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        if (ContextCompat.checkSelfPermission(this, RECORD_AUDIO)
//            != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(RECORD_AUDIO), 1)
//        }
//
//        // SpeechRecognizer 초기화 함수 호출
//        initializeSpeechRecognizer()
//
//        binding.btnMyvoice.setOnClickListener {
//            matchCount = 0
//            startListening()
//        }
//    }
//
//    private fun initializeSpeechRecognizer() {
//        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
//        speechRecognizer.setRecognitionListener(object : RecognitionListener {
//            override fun onReadyForSpeech(params: Bundle?) {
//                Toast.makeText(applicationContext, "녹음 시작", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onBeginningOfSpeech() {}
//
//            override fun onRmsChanged(rmsdB: Float) {}
//
//            override fun onBufferReceived(buffer: ByteArray?) {}
//
//            override fun onEndOfSpeech() {
//                // 녹음이 끝난 후 음성 인식이 완료되길 기다림
//            }
//
//            override fun onError(error: Int) {
//                Toast.makeText(applicationContext, "오류 발생: $error", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResults(results: Bundle?) {
//                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
//                matches?.let {
//                    for (result in it) {
//                        if (result.contains("가나다라마바사")) {
//                            matchCount++
//                        }
//                    }
//                }
//
//                if (matchCount >= 10) {
//                    goToNextActivity()
//                } else {
//                    startListening()
//                }
//            }
//
//            override fun onPartialResults(partialResults: Bundle?) {}
//
//            override fun onEvent(eventType: Int, params: Bundle?) {}
//        })
//    }
//
//    private fun startListening() {
//        speechRecognizer.stopListening()
//        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
//        speechRecognizer.startListening(intent)
//    }
//
//    private fun goToNextActivity() {
//        val intent = Intent(this, RecordActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        speechRecognizer.destroy()
//    }
//}
