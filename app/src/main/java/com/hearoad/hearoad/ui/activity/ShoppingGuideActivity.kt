package com.hearoad.hearoad.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hearoad.hearoad.R
import com.hearoad.hearoad.ui.fragment.ShoppingGuideFragment1
import com.hearoad.hearoad.ui.fragment.ShoppingGuideFragment2
import com.hearoad.hearoad.ui.fragment.ShoppingGuideFragment3

class ShoppingGuideActivity : AppCompatActivity(),
    ShoppingGuideFragment1.OnGuideSelectionListener,
    ShoppingGuideFragment2.OnGuideSelectionListener,
    ShoppingGuideFragment3.OnGuideSelectionListener {

    private lateinit var btnNext: Button
    private lateinit var btnSkip: Button
    private var currentFragmentIndex = 0
    private val fragments = listOf(ShoppingGuideFragment1(), ShoppingGuideFragment2(), ShoppingGuideFragment3())

    private var selectedGuide1: String? = null
    private var selectedGuide2: Uri? = null // Uri로 변경
    private var selectedGuide3: List<String>? = null // List<String>으로 변경

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        btnNext = findViewById(R.id.btn_next)
        btnSkip = findViewById(R.id.btn_skip)

        // 첫 번째 프래그먼트 로드
        loadFragment(fragments[currentFragmentIndex])

        btnNext.setOnClickListener {
            if (currentFragmentIndex < fragments.size - 1) {
                currentFragmentIndex++
                loadFragment(fragments[currentFragmentIndex])
            } else {
                // 모든 선택 완료 후 ChatRoomActivity로 이동
                navigateToChatRoomActivity()
            }
        }

        btnSkip.setOnClickListener {
            finish() // 예시로 액티비티 종료
        }

        // 초기에는 버튼 비활성화
        btnNext.isEnabled = false
    }

    // 프래그먼트를 로드하는 함수 (완료 후 버튼 상태 확인)
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .runOnCommit {
                // 프래그먼트 로드 완료 후 선택된 가이드 확인
                btnNext.isEnabled = isGuideSelected()
            }
            .commit()
    }

    // ShoppingGuideFragment1 및 ShoppingGuideFragment2에서 단일 선택 처리
    override fun onGuideSelected(guide: Any?, fragmentTag: String) {
        when (fragmentTag) {
            "ShoppingGuideFragment1" -> selectedGuide1 = guide as? String
            "ShoppingGuideFragment2" -> selectedGuide2 = guide as? Uri // 이미지 URI로 받음
        }

        // 선택 상태에 따라 버튼 활성화/비활성화
        btnNext.isEnabled = isGuideSelected()
    }

    // ShoppingGuideFragment3에서 여러 선택 처리
    override fun onGuideSelected(guides: List<String>, fragmentTag: String) {
        if (fragmentTag == "ShoppingGuideFragment3") {
            selectedGuide3 = guides
        }

        // 선택 상태에 따라 버튼 활성화/비활성화
        btnNext.isEnabled = isGuideSelected()
    }

    // 선택된 가이드가 있는지 확인하는 메소드
    private fun isGuideSelected(): Boolean {
        return when (currentFragmentIndex) {
            0 -> selectedGuide1 != null
            1 -> selectedGuide2 != null
            2 -> selectedGuide3 != null && selectedGuide3!!.isNotEmpty()
            else -> false
        }
    }

    // 모든 선택 완료 후 ChatRoomActivity로 이동하는 함수
    private fun navigateToChatRoomActivity() {
        val intent = Intent(this, ChatroomActivity::class.java).apply {
            putExtra("selected_guide_1", selectedGuide1)
            putExtra("selected_guide_2", selectedGuide2.toString()) // Uri를 String으로 변환해서 전달
            putExtra("selected_guide_3", selectedGuide3?.joinToString(", ")) // List<String>을 결합하여 전달
        }
        startActivity(intent)
    }
}
