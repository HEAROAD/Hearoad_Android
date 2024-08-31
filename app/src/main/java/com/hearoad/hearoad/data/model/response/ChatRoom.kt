package com.hearoad.hearoad.data.model.response

data class ChatRoom(
    val id: String,
    val title: String,
    val creatorNickname: String,
    val lastMessageTime: String,
    val messages: List<ChatMessage>
)
data class ChatMessage(
    val type: String,
    val message: String,
    val ksl: String?,
    val timestamp: String?
)
