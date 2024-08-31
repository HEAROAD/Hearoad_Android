package com.hearoad.hearoad.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// 데이터 모델 정의
data class ChatRoom(
    val id: Int,
    val name: String,
    val lastMessage: String,
    val timestamp: String
)

// Retrofit Interface 정의
interface ChattingApiService {
    @GET("/api/chat/rooms")
    suspend fun getChatRooms(): List<ChatRoom>
}

// RetrofitChatting 클래스
class RetrofitChatting {

    // Retrofit 인스턴스 생성
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.yourapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // API 서비스 생성
    val apiService: ChattingApiService = retrofit.create(ChattingApiService::class.java)
}
