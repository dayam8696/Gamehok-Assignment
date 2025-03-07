package com.example.gamehok.model

data class Tournament(
    val id: Int,
    val name: String,
    val thumbnailPath: String?,
    val entryFees: Int,
    val prizeCoins: String,
    val tournamentStartTime: Long,
    val registrationEndTime: Long,
    val organizerDetails: OrganizerDetails,
    val status: String,
    val gameName: String,
    val teamSize: String,
    val registeredCount: Int,
    val totalCount: Int
)

data class OrganizerDetails(
    val id: String,
    val name: String,
    val profileImagePath: String?
)
