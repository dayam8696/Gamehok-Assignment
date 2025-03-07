package com.example.gamehok.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    object ApiClient {
        private const val BASE_URL = "https://67c9566f0acf98d07089d293.mockapi.io/"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}