package com.hearoad.hearoad.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hearoad.hearoad.databinding.ActivityRecordBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.IOException

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response



class RecordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecordBinding
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val audioFilePath = intent.getStringExtra("audioFilePath")

        binding.btnRecordRetry.setOnClickListener {
            finish()
        }
        binding.btnRecordComplete.setOnClickListener {
            if (audioFilePath != null) {
                uploadVoiceFile(File(audioFilePath))
            } else {
                Toast.makeText(this, "녹음 파일이 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadVoiceFile(file: File) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.name, file.asRequestBody("audio/mpeg".toMediaTypeOrNull()))
            .build()

        val request = Request.Builder()
            .url("http://your-backend-url.com/myvoice")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@RecordActivity, "파일 업로드 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val resultIntent = Intent(this@RecordActivity, MyvoiceActivity::class.java)
                    resultIntent.putExtra("response", responseBody)
                    startActivity(resultIntent)
                    finish()
                } else {
                    runOnUiThread {
                        Toast.makeText(this@RecordActivity, "파일 업로드 실패: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}