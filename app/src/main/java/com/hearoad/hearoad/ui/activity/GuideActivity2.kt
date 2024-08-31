package com.hearoad.hearoad.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.hearoad.hearoad.R

class GuideActivity2 : AppCompatActivity() {

    private lateinit var btnNext: Button
    private lateinit var btnSkip: Button
    private lateinit var clGuide1: ConstraintLayout
    private lateinit var clGuide2: ConstraintLayout
    private lateinit var clGuide3: ConstraintLayout
    private lateinit var clGuide4: ConstraintLayout
    private lateinit var clGuide5: ConstraintLayout
    private lateinit var sharedPreferences: SharedPreferences

    private var selectedGuide: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_emergancy_02)

        sharedPreferences = getSharedPreferences("selected_item_cache", MODE_PRIVATE)

        btnNext = findViewById(R.id.btn_next)
        btnSkip = findViewById(R.id.btn_skip)
        clGuide1 = findViewById(R.id.cl_guide_1)
        clGuide2 = findViewById(R.id.cl_guide_2)
        clGuide3 = findViewById(R.id.cl_guide_3)
        clGuide4 = findViewById(R.id.cl_guide_4)
        clGuide5 = findViewById(R.id.cl_guide_5)

        clGuide1.setOnClickListener { selectGuide(clGuide1, "guide_1", "나") }
        clGuide2.setOnClickListener { selectGuide(clGuide2, "guide_2", "가족") }
        clGuide3.setOnClickListener { selectGuide(clGuide3, "guide_3", "친구") }
        clGuide4.setOnClickListener { selectGuide(clGuide4, "guide_4", "모르는 사람") }
        clGuide5.setOnClickListener { selectGuide(clGuide5, "guide_5", "직접입력") }

        btnNext.setOnClickListener {
            handleNextButtonClick()
        }

        btnSkip.setOnClickListener {
            handleSkipButtonClick()
        }
    }

    private fun selectGuide(selectedLayout: ConstraintLayout, guideKey: String, guideText: String) {
        selectedGuide?.isSelected = false  // 이전 선택 항목 해제
        selectedLayout.isSelected = true   // 현재 선택 항목 설정
        selectedGuide = selectedLayout     // 선택된 항목 기억

        // 선택된 항목의 텍스트 값을 SharedPreferences에 저장
        val editor = sharedPreferences.edit()
        editor.putString("${guideKey}_text", guideText)
        editor.apply()
    }

    private fun handleNextButtonClick() {
        val intent = Intent(this, GuideActivity3::class.java)
        startActivity(intent)
    }

    private fun handleSkipButtonClick() {
        val intent = Intent(this, GuideActivity3::class.java)
        startActivity(intent)
    }
}
