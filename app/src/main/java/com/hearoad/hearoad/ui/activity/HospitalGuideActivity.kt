package com.hearoad.hearoad.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hearoad.hearoad.R
import com.hearoad.hearoad.ui.fragment.HospitalGuideFragment1
import com.hearoad.hearoad.ui.fragment.HospitalGuideFragment2
import com.hearoad.hearoad.ui.fragment.HospitalGuideFragment3

class HospitalGuideActivity : AppCompatActivity(),
    HospitalGuideFragment1.OnGuideSelectionListener,
    HospitalGuideFragment2.OnGuideSelectionListener,
    HospitalGuideFragment3.OnGuideSelectionListener {

    private lateinit var btnNext: Button
    private lateinit var btnSkip: Button
    private var currentFragmentIndex = 0
    private val fragments = listOf(HospitalGuideFragment1(), HospitalGuideFragment2(), HospitalGuideFragment3())

    private var selectedGuide1: String? = null
    private var selectedGuide2: String? = null
    private var selectedGuide3: String? = null

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
            if (currentFragmentIndex < fragments.size - 1) {
                currentFragmentIndex++  // 프래그먼트 인덱스를 증가시킴
                loadFragment(fragments[currentFragmentIndex])  // 다음 프래그먼트 로드
            } else {
                navigateToChatRoomActivity()
            }
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

    // 가이드 선택 시 호출되는 메소드
    override fun onGuideSelected(guide: String?, fragmentTag: String) {
        when (fragmentTag) {
            "HospitalGuideFragment1" -> selectedGuide1 = guide
            "HospitalGuideFragment2" -> selectedGuide2 = guide
            "HospitalGuideFragment3" -> selectedGuide3 = guide
        }

        // 선택 상태에 따라 버튼 활성화/비활성화
        btnNext.isEnabled = isGuideSelected()
    }


    // 선택된 가이드가 있는지 확인하는 메소드
    private fun isGuideSelected(): Boolean {
        return when (currentFragmentIndex) {
            0 -> selectedGuide1 != null
            1 -> selectedGuide2 != null
            2 -> selectedGuide3 != null
            else -> false
        }
    }

    // 모든 선택 완료 후 ChatRoomActivity로 이동하는 함수
    private fun navigateToChatRoomActivity() {
        val intent = Intent(this, ChatroomActivity::class.java).apply {
            putExtra("selected_guide_1", selectedGuide1)
            putExtra("selected_guide_2", selectedGuide2)
            putExtra("selected_guide_3", selectedGuide3)
        }
        startActivity(intent)
    }
}
