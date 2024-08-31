package com.hearoad.hearoad.data.model.response

data class CreateChatRoomResponse(
    val response: ChatRoomResponseData
)

data class ChatRoomResponseData(
    val roomId: String
)

data class SendMessageResponse(
    val response: Any
)