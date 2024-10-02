package com.hearoad.hearoad.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hearoad.hearoad.R
import com.hearoad.hearoad.ui.fragment.MapGuideFragment1
import com.hearoad.hearoad.ui.fragment.MapGuideFragment2

class MapGuideActivity : AppCompatActivity(),
    MapGuideFragment1.OnGuideSelectionListener { // MapGuideFragment2는 맵에서 선택한 후 버튼 활성화시키는거 추가

    private lateinit var btnNext: Button
    private lateinit var btnSkip: Button
    private var currentFragmentIndex = 0
    private val fragments = listOf(MapGuideFragment1(), MapGuideFragment2())

    private var selectedGuide1: String? = null

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
                // 모든 선택 완료 후 다음 액티비티로 이동
                navigateToNextActivity()
            }
        }

        btnSkip.setOnClickListener {
            finish() // 액티비티 종료
        }

        // 초기에는 버튼 비활성화 (첫 번째 프래그먼트에선 선택할 때까지 비활성화)
        btnNext.isEnabled = false
    }

    // 프래그먼트를 로드하는 함수 (완료 후 버튼 상태 확인)
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .runOnCommit {
                // 첫 번째 프래그먼트에서는 선택 여부에 따라 버튼 활성화
                // 두 번째 프래그먼트는 선택과 상관없이 버튼 활성화
                btnNext.isEnabled = isGuideSelected()
            }
            .commit()
    }

    // 가이드 선택 시 호출되는 메소드 (첫 번째 프래그먼트에서만 사용)
    override fun onGuideSelected(guide: String?, fragmentTag: String) {
        if (fragmentTag == "MapGuideFragment1") {
            selectedGuide1 = guide
        }

        // 첫 번째 프래그먼트 선택 시에만 버튼 활성화
        btnNext.isEnabled = isGuideSelected()
    }

    // 선택된 가이드가 있는지 확인하는 메소드
    private fun isGuideSelected(): Boolean {
        // 첫 번째 프래그먼트에서는 선택 여부 확인, 두 번째 프래그먼트는 항상 true
        return when (currentFragmentIndex) {
            0 -> selectedGuide1 != null
            1 -> true
            else -> false
        }
    }

    // 모든 선택 완료 후 다음 액티비티로 이동하는 함수
    private fun navigateToNextActivity() {
        val intent = Intent(this, ChatroomActivity::class.java).apply {
            putExtra("selected_guide_1", selectedGuide1)
        }
        startActivity(intent)
    }
}
