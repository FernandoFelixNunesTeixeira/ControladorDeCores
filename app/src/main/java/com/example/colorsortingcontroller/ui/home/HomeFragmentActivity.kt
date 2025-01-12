package com.example.colorsortingcontroller.ui.home

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.colorsortingcontroller.HomeViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.example.colorsortingcontroller.theme.ColorSortingControllerTheme


interface SensorBiometrico {
    fun AutenticacaoBiometria()
}

class HomeFragmentActivity : FragmentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: HomeViewModel

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this, HomeViewModelFactory(auth)).get(HomeViewModel::class.java)

        setContent {
            ColorSortingControllerTheme {
                LoadingScreen(isLoading = true)
            }
        }

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            setContent {
                ColorSortingControllerTheme {
                    HomeScreen(viewModel = viewModel)
                }
            }
        }, 3000)

                viewModel.inicializarBiometria(this)
    }


}