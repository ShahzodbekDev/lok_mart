package com.example.locmart.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.locmart.MainDirections
import com.example.locmart.R
import com.example.locmart.databinding.ActivityMainBinding
import com.example.locmart.domain.model.Destination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding


    private val navController get() = ( supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeToLiveData()

    }

    private fun subscribeToLiveData() {
        viewModel.events.observe(this) {
            when (it) {
                is MainViewModel.Event.NavigateTo -> navigateTo(it.destination)
            }
        }
    }

    private fun navigateTo(destination: Destination) {
        when (destination) {
            Destination.Auth -> navController.navigate(MainDirections.toSignInFragment())
            Destination.Home -> navController.navigate(MainDirections.toHomeFragment())
            Destination.OnBoarding -> navController.navigate(MainDirections.toOnboardingFragment())
        }
    }
}