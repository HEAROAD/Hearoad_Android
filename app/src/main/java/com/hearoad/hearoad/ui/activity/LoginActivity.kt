package com.hearoad.hearoad.ui.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.hearoad.hearoad.MainActivity
import com.hearoad.hearoad.data.api.ApiService
import com.hearoad.hearoad.data.model.response.AuthResponse
import com.hearoad.hearoad.data.network.NetworkResult
import com.hearoad.hearoad.data.network.RetrofitClient
import com.hearoad.hearoad.databinding.ActivityLoginBinding
import com.hearoad.hearoad.utils.GlobalApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoginKakao.setOnClickListener {
            //로그인 버튼
            kakaoLogin()
        }
    }

    private fun kakaoLogin() {
        val apiService: ApiService = RetrofitClient.create(ApiService::class.java)
        val call = apiService.getKakaoLogin()
        call.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        handleLoginSuccess(loginResponse.access_token)
                    }
                } else {
                    Log.e("login", "Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e("login", "Failure: ${t.message}", t)
            }
        })
    }
    private fun handleLoginSuccess(accessToken: String) {
        // 로그인 성공 시의 처리 로직
        saveAuthToken(accessToken)
        navigateToMain()
    }

    private fun saveAuthToken(authToken: String) {
        GlobalApplication.spf.accessToken = authToken
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

//    private fun kakaoLogin() {
//        call.enqueue(object : Callback<KakaoLoginResponse> {
//            override fun onResponse(call: Call<KakaoLoginResponse>, response: Response<KakaoLoginResponse>) {
//                if (response.isSuccessful) {
//                    val redirectUrl = response.body()?.redirectUrl
//                    // 사용자를 이 URL로 리디렉션하여 카카오 로그인 페이지로 이동
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(redirectUrl))
//                    startActivity(intent)
//                }
//            }
//
//            override fun onFailure(call: Call<KakaoLoginResponse>, t: Throwable) {
//                // 오류 처리
//            }
//        })
//        call.enqueue(object : Callback<AuthResponse> {
//            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
//                if (response.isSuccessful) {
//                    val authToken = response.body()?.authToken
//                    // 토큰을 안전하게 저장하고, 이후 API 호출 시 사용
//                    saveAuthToken(authToken)
//                    // 사용자를 앱 내 메인 화면으로 이동
//                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//                }
//            }
//
//            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//                // 오류 처리
//            }
//        })
//    }

