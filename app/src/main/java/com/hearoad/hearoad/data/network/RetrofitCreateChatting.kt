package com.hearoad.hearoad.data.network

import android.content.Context
import android.widget.Toast
import com.hearoad.hearoad.data.api.ChatApiService
import com.hearoad.hearoad.data.model.response.CreateChatRoomResponse
import com.hearoad.hearoad.data.model.response.SendMessageResponse
import com.hearoad.hearoad.ui.activity.GuideActivity3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCreateChatting(private val context: Context) {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://your-backend-url.com")  // 실제 서버 URL로 변경하세요
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val chatApiService: ChatApiService by lazy {
        retrofit.create(ChatApiService::class.java)
    }

    fun createChatRoom(token: String, title: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        val chatRoomParams = mapOf("title" to title)

        chatApiService.createChatRoom("Bearer $token", chatRoomParams).enqueue(object : Callback<CreateChatRoomResponse> {
            override fun onResponse(call: Call<CreateChatRoomResponse>, response: Response<CreateChatRoomResponse>) {
                if (response.isSuccessful) {
                    val roomId = response.body()?.response?.roomId ?: return
                    onSuccess(roomId)
                } else {
                    onFailure("채팅방 생성 실패: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CreateChatRoomResponse>, t: Throwable) {
                onFailure("네트워크 오류: ${t.message}")
            }
        })
    }

    fun sendMessage(token: String, roomId: String, message: String, type: String = "USER", ksl: String? = null, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val messageParams = mutableMapOf(
            "type" to type,
            "message" to message
        )
        ksl?.let { messageParams["file"] = it }

        chatApiService.sendMessage("Bearer $token", roomId, messageParams).enqueue(object : Callback<SendMessageResponse> {
            override fun onResponse(call: Call<SendMessageResponse>, response: Response<SendMessageResponse>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure("메시지 전송 실패: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SendMessageResponse>, t: Throwable) {
                onFailure("네트워크 오류: ${t.message}")
            }
        })
    }
}
