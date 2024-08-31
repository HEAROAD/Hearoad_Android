package com.hearoad.hearoad.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class GlobalApplication : Application() {
    companion object {
        lateinit var spf: MySharedPreference
    }

    override fun onCreate() {
        spf = MySharedPreference(applicationContext)
        super.onCreate()
    }
}

class MySharedPreference(context: Context) {

    private val spf = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    var accessToken: String?
        get() = spf.getString("access_token", null)
        set(value) {
            spf.edit().putString("access_token", value).apply()
        }

    fun clearAuthToken() {
        with(spf.edit()) {
            remove("access_token")
            apply()
        }
    }
}
