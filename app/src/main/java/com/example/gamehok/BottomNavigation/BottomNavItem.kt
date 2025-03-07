package com.example.gamehok.BottomNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Tournaments : BottomNavItem("tournaments", Icons.Default.EmojiEvents, "MyTournament")
    object Profile : BottomNavItem("profile", Icons.Default.Person2, "Socials")
    object Chat : BottomNavItem("chat", Icons.Default.Chat, "chat")
}