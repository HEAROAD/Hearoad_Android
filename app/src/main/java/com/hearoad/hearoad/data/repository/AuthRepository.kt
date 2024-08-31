package com.hearoad.hearoad.data.repository

import com.hearoad.hearoad.data.api.ApiService
import com.hearoad.hearoad.data.model.response.AuthResponse
import com.hearoad.hearoad.data.network.NetworkResult
import com.hearoad.hearoad.data.network.RetrofitClient

//class AuthRepository {
//
//    private val authApi = RetrofitClient.create(ApiService::class.java)
//
//    suspend fun loginWithKakao(authorizationCode: String): NetworkResult<AuthResponse> {
//        return try {
//            val response = authApi.completeKakaoLogin(authorizationCode)
//            NetworkResult.Success(response)
//        } catch (e: Exception) {
//            NetworkResult.Error(e.message ?: "Unknown Error")
//        }
//    }
//}