package com.example.reservation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reservation.Ui.Screen.UserInfosScreen
import com.example.reservation.Ui.Screen.SportSelectionScreen
import com.example.reservation.Ui.Screen.HomeScreen
import com.example.reservation.Ui.Screen.SearchStadiums
import com.example.reservation.Ui.Screen.ReservationsScreen
import com.example.reservation.Ui.Screen.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    NavHost(
                        navController = navController,
                        startDestination = "user_info"
                    ) {
                        composable("user_info") {
                            UserInfosScreen(onContinue = { firstName ->
                                navController.navigate("sport_selection/$firstName")
                            })
                        }
                        composable(
                            route = "sport_selection/{name}",
                            arguments = listOf(navArgument("name") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val name = backStackEntry.arguments?.getString("name") ?: ""
                            SportSelectionScreen(
                                userName = name,
                                onSportSelected = { sport ->
                                    navController.navigate("home")
                                }
                            )
                        }
                        composable("home") {
                            HomeScreen(onNavigate = { route ->
                                navController.navigate(route)
                            })
                        }
                        composable("search") {
                            SearchStadiums(onNavigate = { route ->
                                if (route == "home") {
                                    navController.popBackStack("home", inclusive = false)
                                } else {
                                    navController.navigate(route)
                                }
                            })
                        }
                        composable("reservations") {
                            ReservationsScreen(onNavigate = { route ->
                                if (route == "home") {
                                    navController.popBackStack("home", inclusive = false)
                                } else {
                                    navController.navigate(route)
                                }
                            })
                        }
                        composable("profile") {
                            ProfileScreen(
                                onNavigate = { route ->
                                    if (route == "home") {
                                        navController.popBackStack("home", inclusive = false)
                                    } else {
                                        navController.navigate(route)
                                    }
                                },
                                onLogout = {
                                    navController.navigate("user_info") {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
