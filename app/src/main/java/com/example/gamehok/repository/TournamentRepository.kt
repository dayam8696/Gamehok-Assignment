package com.example.gamehok.repository

import com.example.gamehok.api.ApiClient
import com.example.gamehok.api.TournamentApi
import com.example.gamehok.model.Tournament

import retrofit2.Response

class TournamentRepository private constructor() {

    private val apiService = ApiClient.ApiClient.retrofit.create(TournamentApi::class.java)

    suspend fun getTournaments(): Response<List<Tournament>> {
        return apiService.getTournaments()
    }

    companion object {
        @Volatile
        private var instance: TournamentRepository? = null

        fun getInstance(): TournamentRepository {
            return instance ?: synchronized(this) {
                instance ?: TournamentRepository().also { instance = it }
            }
        }
    }
}
