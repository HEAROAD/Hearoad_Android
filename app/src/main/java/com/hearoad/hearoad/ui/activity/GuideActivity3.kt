package com.hearoad.hearoad.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.hearoad.hearoad.R
import com.hearoad.hearoad.data.network.RetrofitCreateChatting
import com.hearoad.hearoad.utils.GlobalApplication

class GuideActivity3 : AppCompatActivity() {

    private lateinit var btnNext: Button
    private lateinit var clGuide1: ConstraintLayout
    private lateinit var clGuide2: ConstraintLayout
    private lateinit var clGuide3: ConstraintLayout
    private lateinit var clGuide4: ConstraintLayout
    private lateinit var clGuide5: ConstraintLayout
    private lateinit var sharedPreferences: SharedPreferences

    private var selectedGuide: ConstraintLayout? = null
    private val token = GlobalApplication.spf.accessToken!!
    private val retrofitCreateChatting by lazy { RetrofitCreateChatting(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_emergancy_03)

        sharedPreferences = getSharedPreferences("selected_item_cache", MODE_PRIVATE)

        btnNext = findViewById(R.id.btn_next)
        clGuide1 = findViewById(R.id.cl_guide_1)
        clGuide2 = findViewById(R.id.cl_guide_2)
        clGuide3 = findViewById(R.id.cl_guide_3)
        clGuide4 = findViewById(R.id.cl_guide_4)
        clGuide5 = findViewById(R.id.cl_guide_5)

        clGuide1.setOnClickListener { selectGuide(clGuide1, "다쳤어요") }
        clGuide2.setOnClickListener { selectGuide(clGuide2, "아파요") }
        clGuide3.setOnClickListener { selectGuide(clGuide3, "사라졌어요") }
        clGuide4.setOnClickListener { selectGuide(clGuide4, "제3자에 의해 위협을 당했어요") }
        clGuide5.setOnClickListener { selectGuide(clGuide5, "직접입력") }

        btnNext.setOnClickListener {
            saveCombinedSelection() // 결합된 선택을 저장
            showStoredValues() // 저장된 값들을 확인하기 위해 Toast로 출력
            createChatRoomAndSendMessages()
        }
    }

    private fun selectGuide(selectedLayout: ConstraintLayout, guideText: String) {
        selectedGuide?.isSelected = false  // 이전 선택 항목 해제
        selectedLayout.isSelected = true   // 현재 선택 항목 설정
        selectedGuide = selectedLayout     // 선택된 항목 기억

        // 현재 선택된 항목의 텍스트 값을 SharedPreferences에 저장
        val editor = sharedPreferences.edit()
        editor.putString("current_guide_text", guideText)
        editor.apply()
    }

    private fun saveCombinedSelection() {
        // 이전 선택과 현재 선택을 불러오기
        val previousGuideText = sharedPreferences.getString("previous_guide_text", "")
        val currentGuideText = sharedPreferences.getString("current_guide_text", "")

        // 두 텍스트를 결합하여 하나의 값으로 저장
        val combinedText = "$previousGuideText $currentGuideText"

        // 결합된 텍스트를 SharedPreferences에 저장
        val editor = sharedPreferences.edit()
        editor.putString("combined_guide_text", combinedText)
        editor.apply()
    }

    private fun showStoredValues() {
        // 결합된 텍스트 값을 불러옵니다.
        val combinedGuideText = sharedPreferences.getString("combined_guide_text", "선택된 항목 없음")

        // 결합된 텍스트를 Toast로 출력하여 확인
        Toast.makeText(this, combinedGuideText, Toast.LENGTH_LONG).show()
    }

    private fun createChatRoomAndSendMessages() {
        val chatRoomTitle = "목소리 등록" // 실제 채팅방 이름 설정

        retrofitCreateChatting.createChatRoom(token, chatRoomTitle,
            onSuccess = { roomId ->
                sendSelectedMessages(roomId)
            },
            onFailure = { errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            })
    }

    private fun showToastWithFailureMessage(guide: String, errorMessage: String) {
        // 오류 메시지를 포함한 Toast 메시지 표시
        Toast.makeText(this, "메시지 전송 실패: $guide, 오류: $errorMessage", Toast.LENGTH_LONG).show()
    }

    private fun sendSelectedMessages(roomId: String) {
        val combinedGuideText = sharedPreferences.getString("combined_guide_text", null)

        if (combinedGuideText != null) {
            retrofitCreateChatting.sendMessage(token, roomId, combinedGuideText,
                onSuccess = {
                    // 메시지 전송 성공 처리
                },
                onFailure = { errorMessage ->
                    showToastWithFailureMessage(combinedGuideText, errorMessage)
                })
        }
    }
}
