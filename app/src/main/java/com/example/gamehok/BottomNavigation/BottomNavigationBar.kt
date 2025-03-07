package com.example.gamehok.BottomNavigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Tournaments,
        BottomNavItem.Profile,
        BottomNavItem.Chat
    )

    NavigationBar(
        containerColor = Color(0xFF001208) // Your background color
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title, color = Color.White) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF00FF00), // Green when selected
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}
