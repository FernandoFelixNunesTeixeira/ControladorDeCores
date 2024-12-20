package com.example.colorsortingcontroller.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.colorsortingcontroller.data.ParametrosRepository

class ParametrosViewModelFactory(
    private val parametrosRepository: ParametrosRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParametrosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ParametrosViewModel(parametrosRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}