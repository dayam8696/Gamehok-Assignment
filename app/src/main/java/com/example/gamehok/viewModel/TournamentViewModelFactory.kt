package com.example.gamehok.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gamehok.repository.TournamentRepository

class TournamentViewModelFactory(private val repository: TournamentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TournamentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TournamentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}