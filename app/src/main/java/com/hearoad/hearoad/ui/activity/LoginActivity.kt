package com.hearoad.hearoad.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hearoad.hearoad.MainActivity
import com.hearoad.hearoad.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoginKakao.setOnClickListener {
            // 로그인 버튼 클릭 시 바로 MainActivity로 이동
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // 현재 로그인 액티비티를 종료하여 뒤로 가기 시 다시 나타나지 않도록 함
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

