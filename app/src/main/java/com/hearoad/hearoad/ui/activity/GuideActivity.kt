package com.hearoad.hearoad.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.hearoad.hearoad.R

class GuideActivity : AppCompatActivity() {

    private lateinit var btnNext: Button
    private lateinit var btnSkip: Button
    private lateinit var clGuide1: ConstraintLayout
    private lateinit var clGuide2: ConstraintLayout
    private lateinit var sharedPreferences: SharedPreferences

    private var selectedGuide: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_emergancy_01)

        sharedPreferences = getSharedPreferences("selected_item_cache", MODE_PRIVATE)

        btnNext = findViewById(R.id.btn_next)
        btnSkip = findViewById(R.id.btn_skip)
        clGuide1 = findViewById(R.id.cl_guide_1)
        clGuide2 = findViewById(R.id.cl_guide_2)

        clGuide1.setOnClickListener {
            selectGuide(clGuide1, "guide_1")
        }

        clGuide2.setOnClickListener {
            selectGuide(clGuide2, "guide_2")
        }

        btnNext.setOnClickListener {
            handleNextButtonClick()
        }

        btnSkip.setOnClickListener {
            handleSkipButtonClick()
        }
    }

    private fun selectGuide(selectedLayout: ConstraintLayout, guide: String) {
        selectedGuide?.isSelected = false  // 이전 선택 항목 해제
        selectedLayout.isSelected = true   // 현재 선택 항목 설정
        selectedGuide = selectedLayout     // 선택된 항목 기억

        saveSelectedItem(guide)
    }

    private fun saveSelectedItem(item: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_guide_1_selected", item == "guide_1")
        editor.putBoolean("is_guide_2_selected", item == "guide_2")
        editor.apply()
    }

    private fun handleNextButtonClick() {
        val isGuide1Selected = sharedPreferences.getBoolean("is_guide_1_selected", false)
        val isGuide2Selected = sharedPreferences.getBoolean("is_guide_2_selected", false)

        val intent = Intent(this, GuideActivity2::class.java)
        when {
            isGuide1Selected -> intent.putExtra("selected_item", "guide_1")
            isGuide2Selected -> intent.putExtra("selected_item", "guide_2")
            else -> {
                Toast.makeText(this, "항목을 선택해 주세요.", Toast.LENGTH_SHORT).show()
                return
            }
        }
        startActivity(intent)
    }

    private fun handleSkipButtonClick() {
        val intent = Intent(this, GuideActivity2::class.java)
        startActivity(intent)
    }
}
