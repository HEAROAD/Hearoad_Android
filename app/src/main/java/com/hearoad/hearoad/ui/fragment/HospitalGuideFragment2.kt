package com.hearoad.hearoad.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.hearoad.hearoad.R

class HospitalGuideFragment2 : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private var selectedGuideLayout: ConstraintLayout? = null  // 선택된 레이아웃
    private var selectedGuide: String? = null  // 선택된 가이드 ID

    private var callback: OnGuideSelectionListener? = null

    // 인터페이스에서 guide 매개변수를 nullable로 변경
    interface OnGuideSelectionListener {
        fun onGuideSelected(guide: String?, fragmentTag: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnGuideSelectionListener) {
            callback = context
        } else {
            throw RuntimeException("$context must implement OnGuideSelectionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guide_hospital_02, container, false)

        sharedPreferences = requireContext().getSharedPreferences("guide_prefs", Context.MODE_PRIVATE)

        // UI 요소 초기화
        val guide1Layout: ConstraintLayout = view.findViewById(R.id.cl_guide_1)
        val guide2Layout: ConstraintLayout = view.findViewById(R.id.cl_guide_2)
        val guide3Layout: ConstraintLayout = view.findViewById(R.id.cl_guide_3)
        val guide4Layout: ConstraintLayout = view.findViewById(R.id.cl_guide_4)
        val guide5Layout: ConstraintLayout = view.findViewById(R.id.cl_guide_5)
        val guide6Layout: ConstraintLayout = view.findViewById(R.id.cl_guide_6)

        // 이전에 저장된 선택 상태 불러오기
        loadSelectedGuide(guide1Layout, guide2Layout, guide3Layout, guide4Layout, guide5Layout,guide6Layout)

        // 클릭 이벤트 공통 처리
        guide1Layout.setOnClickListener {
            handleGuideSelection(guide1Layout, "guide_1")
        }

        guide2Layout.setOnClickListener {
            handleGuideSelection(guide2Layout, "guide_2")
        }

        guide3Layout.setOnClickListener {
            handleGuideSelection(guide3Layout, "guide_3")
        }

        guide4Layout.setOnClickListener {
            handleGuideSelection(guide4Layout, "guide_4")
        }

        guide5Layout.setOnClickListener {
            handleGuideSelection(guide5Layout, "guide_5")
        }

        guide6Layout.setOnClickListener {
            handleGuideSelection(guide6Layout, "guide_6")
        }

        return view
    }

    private fun handleGuideSelection(selectedLayout: ConstraintLayout, guideId: String) {
        // 선택된 guideId에 따라 하드코딩된 텍스트 값 설정
        val guideValue = when (guideId) {
            "guide_1" -> "머리"
            "guide_2" -> "목"
            "guide_3" -> "가슴"
            "guide_4" -> "배"
            "guide_5" -> "다리"
            "guide_6" -> "직접 입력"
            else -> ""
        }

        // 이미 선택된 가이드를 다시 클릭한 경우 선택 해제
        if (selectedGuideLayout == selectedLayout) {
            selectedLayout.setBackgroundResource(R.drawable.shape_roundborder_10)  // 기본 배경으로 복구
            selectedGuideLayout = null
            selectedGuide = null
            clearSelectedGuide()  // SharedPreferences에서 선택 해제
            callback?.onGuideSelected(null, "HospitalGuideFragment2")  // 선택 해제 상태 전달
        } else {
            // 이전 선택 항목 해제
            selectedGuideLayout?.setBackgroundResource(R.drawable.shape_roundborder_10)  // 기본 배경으로 복구

            // 새로 선택된 항목 설정
            selectedLayout.setBackgroundResource(R.drawable.shape_selected)
            selectedGuideLayout = selectedLayout
            selectedGuide = guideValue  // 하드코딩된 값 사용

            // 선택 상태 저장
            saveSelectedGuide(guideValue)

            // 선택된 value 값을 GuideActivity에 전달
            callback?.onGuideSelected(guideValue, "HospitalGuideFragment2")
        }
    }


    private fun saveSelectedGuide(guide: String) {
        val editor = sharedPreferences.edit()
        editor.putString("selected_guide_2", guide)
        editor.apply()
    }

    private fun clearSelectedGuide() {
        val editor = sharedPreferences.edit()
        editor.remove("selected_guide_2")  // SharedPreferences에서 선택 해제
        editor.apply()
    }

    private fun loadSelectedGuide(
        guide1Layout: ConstraintLayout,
        guide2Layout: ConstraintLayout,
        guide3Layout: ConstraintLayout,
        guide4Layout: ConstraintLayout,
        guide5Layout: ConstraintLayout,
        guide6Layout: ConstraintLayout
    ) {
        // 모든 가이드 항목을 초기화 (기본 상태로 복구)
        guide1Layout.setBackgroundResource(R.drawable.shape_roundborder_10)
        guide2Layout.setBackgroundResource(R.drawable.shape_roundborder_10)
        guide3Layout.setBackgroundResource(R.drawable.shape_roundborder_10)
        guide4Layout.setBackgroundResource(R.drawable.shape_roundborder_10)
        guide5Layout.setBackgroundResource(R.drawable.shape_roundborder_10)
        guide6Layout.setBackgroundResource(R.drawable.shape_roundborder_10)

        val selectedGuideKey = sharedPreferences.getString("selected_guide_2", null)

        // 선택된 가이드가 있으면 복원
        when (selectedGuideKey) {
            "guide_1" -> {
                //guide1Layout.setBackgroundResource(R.drawable.shape_selected)
                selectedGuideLayout = guide1Layout
            }
            "guide_2" -> {
                //guide2Layout.setBackgroundResource(R.drawable.shape_selected)
                selectedGuideLayout = guide2Layout
            }
            "guide_3" -> {
                //guide3Layout.setBackgroundResource(R.drawable.shape_selected)
                selectedGuideLayout = guide3Layout
            }
            "guide_4" -> {
                //guide4Layout.setBackgroundResource(R.drawable.shape_selected)
                selectedGuideLayout = guide4Layout
            }
            "guide_5" -> {
                //guide5Layout.setBackgroundResource(R.drawable.shape_selected)
                selectedGuideLayout = guide5Layout
            }
            "guide_6" -> {
                //guide6Layout.setBackgroundResource(R.drawable.shape_selected)
                selectedGuideLayout = guide6Layout
            }
            else -> {
                // 선택된 가이드가 없을 때 초기화
                selectedGuideLayout = null
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }
}
