package com.example.gamehok.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamehok.model.Game

import com.example.gamehok.model.Tournament
import com.example.gamehok.repository.TournamentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TournamentViewModel(private val repository: TournamentRepository) : ViewModel() {

    private val _tournaments = MutableStateFlow<List<Tournament>>(emptyList())
    val tournaments: StateFlow<List<Tournament>> = _tournaments

    private val _gameCategories = MutableStateFlow<List<Game>>(emptyList())
    val gameCategories: StateFlow<List<Game>> = _gameCategories

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val tournamentResponse = repository.getTournaments()
                val gameResponse = repository.getGames()

                if (tournamentResponse.isSuccessful) {
                    _tournaments.value = tournamentResponse.body() ?: emptyList()
                } else {
                    _error.value = "Error fetching tournaments"
                }

                if (gameResponse.isSuccessful) {
                    _gameCategories.value = gameResponse.body() ?: emptyList()
                } else {
                    _error.value = "Error fetching games"
                }

            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Unknown error"
            }
            _isLoading.value = false
        }
    }
}