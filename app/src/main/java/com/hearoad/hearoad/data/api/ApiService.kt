package com.hearoad.hearoad.data.api

import com.hearoad.hearoad.data.model.request.SoundRequest
import com.hearoad.hearoad.data.model.response.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
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

    //응급보이스
    @POST("/api/voice/upload")
    suspend fun addVoice(
        @Header("Authorization") token: String,
        @Body soundReqest: SoundRequest
    ): Response<AddVoiceResponse>

    @GET("/api/voice/files")
    suspend fun getEmergencyVoices(
        @Header("Authorization") token: String
    ): Response<List<EmergencyVoiceResponse>>
}
