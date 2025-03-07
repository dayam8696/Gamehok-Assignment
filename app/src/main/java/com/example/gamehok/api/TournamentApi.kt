package com.example.gamehok.api

import com.example.gamehok.model.Game
import com.example.gamehok.model.Tournament
import retrofit2.Response
import retrofit2.http.GET

interface TournamentApi {
    @GET("tournaments")
    suspend fun getTournaments(): Response<List<Tournament>>


    @GET("games")
    suspend fun getGames(): Response<List<Game>>
}