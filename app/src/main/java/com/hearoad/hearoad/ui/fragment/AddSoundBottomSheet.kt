package com.hearoad.hearoad.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hearoad.hearoad.data.api.ApiService
import com.hearoad.hearoad.data.model.request.SoundRequest
import com.hearoad.hearoad.data.network.RetrofitClient
import com.hearoad.hearoad.databinding.BottomsheetAddsoundBinding
import com.hearoad.hearoad.utils.GlobalApplication
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException

class AddSoundBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomsheetAddsoundBinding? = null
    private val binding get() = _binding!!
//    private val soundViewModel: SoundViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetAddsoundBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddvoiceCancel.setOnClickListener {
            dismiss()
        }

        binding.btnAddvoiceAdd.setOnClickListener {
            val word = binding.etAddvoiceWord.text.toString()
            val emoji = binding.etAddvoiceImg.text.toString()

            if (word.isNotEmpty()) {
                addSound(word, emoji)
            } else {
                Toast.makeText(requireContext(), "단어를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addSound(word: String, emoji: String) {
        val token = "Bearer " + GlobalApplication.spf.accessToken
        val soundRequest = SoundRequest(word, emoji)
        val apiService = RetrofitClient.instance.create(ApiService::class.java)

        lifecycleScope.launch {
            try {
                val response = apiService.addVoice(token, soundRequest)
                if (response.isSuccessful) {
                    val uploadResponse = response.body()
                    if (uploadResponse?.message == "MP3 파일이 생성되었습니다.") {
                        dismiss()
                    } else {
                        Toast.makeText(context, "응답 메시지가 다릅니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "서버 응답에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(context, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "AddSoundBottomSheet"
    }
}