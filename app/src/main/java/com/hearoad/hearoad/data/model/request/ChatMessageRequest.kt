package com.hearoad.hearoad.data.model.request

import okhttp3.MultipartBody

data class ChatMessageRequest(
    val type: String,
    val message: String,
    val file: String?
//    val file: MultipartBody.Part?
)
