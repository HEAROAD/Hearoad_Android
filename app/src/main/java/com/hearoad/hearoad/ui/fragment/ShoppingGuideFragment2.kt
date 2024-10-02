package com.hearoad.hearoad.ui.fragment

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.hearoad.hearoad.databinding.FragmentGuideShopping02Binding

class ShoppingGuideFragment2 : Fragment() {

    private var _binding: FragmentGuideShopping02Binding? = null
    private val binding get() = _binding!!

    private var callback: OnGuideSelectionListener? = null

    interface OnGuideSelectionListener {
        fun onGuideSelected(guide: Any?, fragmentTag: String)
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            callback?.onGuideSelected(it, "ShoppingGuideFragment2") // Uri 전달
        }
    }

    private val captureImageLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        bitmap?.let {
            callback?.onGuideSelected(it, "ShoppingGuideFragment2") // Bitmap 전달
        }
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
        _binding = FragmentGuideShopping02Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOpenGallery.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.btnOpenCamera.setOnClickListener {
            captureImageLauncher.launch(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }
}
