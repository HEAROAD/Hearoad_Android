package com.hearoad.hearoad.ui

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.hearoad.hearoad.databinding.ToolbarBinding

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: ToolbarBinding

    init {
        binding = ToolbarBinding.inflate(LayoutInflater.from(context), this, true)


        binding.btnToolbarBack.setOnClickListener {
            (context as? Activity)?.finish()
        }

    }

    fun setToolbarText(text: String) {
        binding.tvToolbar.text = text
    }

    fun setButtonVisibility(isVisible: Boolean) {
        binding.btnToolbarGuide.visibility = View.VISIBLE
    }
    fun setButtonGone(isVisible: Boolean) {
        binding.btnToolbarBack.visibility = View.GONE
    }

//    fun setButtonClickListener(listener: OnClickListener) {
//        binding.btnToolbarClose.setOnClickListener(listener)
//    }
}