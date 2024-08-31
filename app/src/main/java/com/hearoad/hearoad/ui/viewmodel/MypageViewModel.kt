package com.hearoad.hearoad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hearoad.hearoad.data.api.ApiService
import com.hearoad.hearoad.data.model.response.MypageResponse
import com.hearoad.hearoad.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MypageViewModel : ViewModel() {

    private val apiService: ApiService = RetrofitClient.create(ApiService::class.java)

    private val _mypageData = MutableLiveData<MypageResponse>()
    val mypageData: LiveData<MypageResponse> get() = _mypageData

    fun fetchMypageData(token: String) {
        val call = apiService.getMypageData("Bearer $token")
        call.enqueue(object : Callback<MypageResponse> {
            override fun onResponse(call: Call<MypageResponse>, response: Response<MypageResponse>) {
                if (response.isSuccessful) {
                    _mypageData.value = response.body()
                }else {
                    Log.e("MypageViewModel", "Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MypageResponse>, t: Throwable) {
                Log.e("MypageViewModel", "Failure: ${t.message}", t)

            }
        })
    }
}
