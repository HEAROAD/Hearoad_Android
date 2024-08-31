package com.hearoad.hearoad.data.api

import com.hearoad.hearoad.data.model.response.AuthResponse
import com.hearoad.hearoad.data.model.response.MypageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
//    @POST("auth/kakao/complete")
//    suspend fun completeKakaoLogin(@Body authorizationCode: String): AuthResponse

    //로그인
    @POST("/api/users/login/page")
    fun getKakaoLogin(): Call<AuthResponse>

    //마이페이지
    @GET("/api/users/mypage")
    fun getMypageData(@Header("Authorization") authToken: String): Call<MypageResponse>
}
