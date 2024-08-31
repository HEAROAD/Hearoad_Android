package com.hearoad.hearoad.data.api

import com.hearoad.hearoad.data.model.response.CreateChatRoomResponse
import com.hearoad.hearoad.data.model.response.SendMessageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface ChatApiService {
    @POST("/api/chat/rooms")
    fun createChatRoom(
        @Header("Authorization") token: String,
        @Body params: Map<String, String>
    ): Call<CreateChatRoomResponse>

    @POST("/api/chat/rooms/{roomId}/messages")
    fun sendMessage(
        @Header("Authorization") token: String,
        @Path("roomId") roomId: String,
        @Body params: Map<String, Any>
    ): Call<SendMessageResponse>
}