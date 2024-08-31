package com.hearoad.hearoad.data.model.response

data class AuthResponse(
    val access_token: String,
    val token_type: String,
    val refresh_token: String,
    val expires_in: Int,
    val scope: String,
    val refresh_token_expires_in: Int
)

data class MypageResponse(
    val nickname: String,
    val character: String
)
