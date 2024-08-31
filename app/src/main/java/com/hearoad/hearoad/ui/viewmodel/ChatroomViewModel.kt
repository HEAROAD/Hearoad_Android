package com.hearoad.hearoad.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hearoad.hearoad.data.api.ApiService
import com.hearoad.hearoad.data.model.request.ChatMessageRequest
import com.hearoad.hearoad.data.model.response.ChatMessage
import com.hearoad.hearoad.data.network.RetrofitClient
import kotlinx.coroutines.launch

class ChatroomViewModel : ViewModel() {

    private val _messages = MutableLiveData<List<ChatMessage>>()
    val messages: LiveData<List<ChatMessage>> get() = _messages

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val chatApi = RetrofitClient.create(ApiService::class.java)

    fun getChatRoomMessages(roomId: String, token: String) {
        viewModelScope.launch {
            try {
                val response = chatApi.getChatRoomMessages(roomId, "Bearer $token")
                if (response.isSuccessful) {
                    response.body()?.let { chatRoom ->
                        _title.postValue(chatRoom.title)
                        _messages.postValue(chatRoom.messages)
                    }
                }
            } catch (e: Exception) {
                // 예외 처리
                e.printStackTrace()
            }
        }
    }

    fun sendMessage(roomId: String, token: String, message: String) {
        viewModelScope.launch {
            try {
                val messageRequest = ChatMessageRequest(
                    type = "PARTNER",
                    message = message,
                    file = ""
                )
                val response = chatApi.sendMessage(roomId, "Bearer $token", messageRequest)
                if (!response.isSuccessful) {
                    // 실패 처리

                }
            } catch (e: Exception) {
                // 예외 처리
                e.printStackTrace()
            }
        }
    }
}
