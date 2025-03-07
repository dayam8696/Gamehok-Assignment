package com.example.gamehok.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gamehok.model.Tournament
import com.example.gamehok.repository.TournamentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TournamentViewModel(private val repository: TournamentRepository) : ViewModel() {

    private val _tournaments = MutableStateFlow<List<Tournament>>(emptyList())
    val tournaments: StateFlow<List<Tournament>> = _tournaments

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchTournaments()
    }

    private fun fetchTournaments() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getTournaments()
                if (response.isSuccessful) {
                    _tournaments.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error fetching data"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Unknown error"
            }
            _isLoading.value = false
        }
    }
}
