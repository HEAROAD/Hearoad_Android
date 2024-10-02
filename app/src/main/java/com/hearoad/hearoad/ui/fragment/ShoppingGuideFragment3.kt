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

class ShoppingGuideFragment3 : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private val selectedGuides: MutableList<String> = mutableListOf()  // 여러 선택 항목 저장 리스트
    private val selectedGuideLayouts: MutableList<ConstraintLayout> = mutableListOf()  // 선택된 레이아웃 리스트

    private var callback: OnGuideSelectionListener? = null

    // 인터페이스에서 guide 매개변수를 nullable로 변경
    interface OnGuideSelectionListener {
        fun onGuideSelected(guides: List<String>, fragmentTag: String)
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
        val view = inflater.inflate(R.layout.fragment_guide_shopping_03, container, false)

        sharedPreferences = requireContext().getSharedPreferences("guide_prefs", Context.MODE_PRIVATE)

        // UI 요소 초기화
        val guide1Layout: ConstraintLayout = view.findViewById(R.id.cl_guide_1)
        val guide2Layout: ConstraintLayout = view.findViewById(R.id.cl_guide_2)
        val guide3Layout: ConstraintLayout = view.findViewById(R.id.cl_guide_3)
        val guide4Layout: ConstraintLayout = view.findViewById(R.id.cl_guide_4)
        val guide5Layout: ConstraintLayout = view.findViewById(R.id.cl_guide_5)
        val guide6Layout: ConstraintLayout = view.findViewById(R.id.cl_guide_6)

        // 클릭 이벤트 공통 처리
        guide1Layout.setOnClickListener {
            handleGuideSelection(guide1Layout, "영수증 주세요")
        }
        guide2Layout.setOnClickListener {
            handleGuideSelection(guide2Layout, "봉투 주세요")
        }
        guide3Layout.setOnClickListener {
            handleGuideSelection(guide3Layout, "주차권 주세요")
        }
        guide4Layout.setOnClickListener {
            handleGuideSelection(guide4Layout, "분실 했어요")
        }
        guide5Layout.setOnClickListener {
            handleGuideSelection(guide5Layout, "일회용품 주세요")
        }
        guide6Layout.setOnClickListener {
            handleGuideSelection(guide6Layout, "직접 입력")
        }

        return view
    }

    private fun handleGuideSelection(selectedLayout: ConstraintLayout, guideValue: String) {
        if (selectedGuides.contains(guideValue)) {
            // 선택 해제
            selectedLayout.setBackgroundResource(R.drawable.shape_roundborder_10)
            selectedGuides.remove(guideValue)
            selectedGuideLayouts.remove(selectedLayout)
        } else {
            // 선택
            selectedLayout.setBackgroundResource(R.drawable.shape_selected)
            selectedGuides.add(guideValue)
            selectedGuideLayouts.add(selectedLayout)
        }

        // 선택된 가이드들을 GuideActivity에 전달
        callback?.onGuideSelected(selectedGuides, "ShoppingGuideFragment3")
    }

    // 선택 상태 저장 (여러 개의 선택을 SharedPreferences에 저장할 경우 수정 필요)
    private fun saveSelectedGuides() {
        val editor = sharedPreferences.edit()
        editor.putStringSet("selected_guide_3", selectedGuides.toSet())
        editor.apply()
    }

    private fun clearSelectedGuides() {
        val editor = sharedPreferences.edit()
        editor.remove("selected_guide_3")
        editor.apply()
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }
}
