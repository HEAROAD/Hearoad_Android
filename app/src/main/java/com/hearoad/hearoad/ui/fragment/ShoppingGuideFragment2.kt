package com.hearoad.hearoad.ui.fragment

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.hearoad.hearoad.BuildConfig
import com.hearoad.hearoad.R
import com.hearoad.hearoad.databinding.FragmentGuideShopping02Binding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ShoppingGuideFragment2 : Fragment() {

    private var _binding: FragmentGuideShopping02Binding? = null
    private val binding get() = _binding!!

    private var callback: OnGuideSelectionListener? = null

    private var imageUri: Uri? = null

    interface OnGuideSelectionListener {
        fun onGuideSelected(guide: Any?, fragmentTag: String)
    }

    // 갤러리에서 이미지 선택
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            callback?.onGuideSelected(it, "ShoppingGuideFragment2") // Uri 전달
        }
    }

    // 카메라로 사진 촬영 후 이미지 저장
    private val captureImageLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
        if (success) {
            imageUri?.let {
                callback?.onGuideSelected(it, "ShoppingGuideFragment2") // 저장된 이미지 Uri 전달
                moveToNextFragment()
            }
        }
    }

    private fun moveToNextFragment() {
        // 프래그먼트를 전환하는 로직
        val nextFragment = ShoppingGuideFragment3()  // 이동할 다음 프래그먼트 인스턴스 생성
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, nextFragment)  // 프래그먼트를 교체
            .addToBackStack(null)  // 이전 프래그먼트로 돌아갈 수 있도록 백스택에 추가
            .commit()
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
            imageUri = createImageUri()  // 이미지 저장할 Uri 생성
            captureImageLauncher.launch(imageUri)
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

    // 이미지를 저장할 Uri 생성하는 함수
    private fun createImageUri(): Uri? {
        return try {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)

            FileProvider.getUriForFile(
                requireContext(),
                "${BuildConfig.APPLICATION_ID}.provider",
                imageFile
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
