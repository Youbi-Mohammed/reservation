package com.example.reservation.Ui.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppBottomNavigationBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple("Accueil", Icons.Default.Home, "home"),
            Triple("Recherche", Icons.Default.Search, "search"),
            Triple("Réservations", Icons.Default.CalendarToday, "reservations"),
            Triple("Profil", Icons.Default.Person, "profile")
        )

        items.forEach { (label, icon, route) ->
            NavigationBarItem(
                selected = currentRoute.startsWith(route),
                onClick = { onNavigate(route) },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label, fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF00A669),
                    selectedTextColor = Color(0xFF00A669),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFFE6F6F0)
                )
            )
        }
    }
}
