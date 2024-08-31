package com.hearoad.hearoad

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.hearoad.hearoad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navMain
        val navController = findNavController(R.id.fragment_nav)

        binding.btnMainCamera.setOnClickListener {
            navController.navigate(R.id.navigation_home)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> {
                    // 홈 화면일 때 이미지 변경
                    binding.btnMainCamera.setImageResource(R.drawable.ic_home_on)
                }
                else -> {
                    // 다른 화면일 때 이미지 변경
                    binding.btnMainCamera.setImageResource(R.drawable.ic_home_off)
                }
            }
        }

        navView.setupWithNavController(navController)
    }
}