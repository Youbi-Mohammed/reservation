package com.example.reservation.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class Loginviewmodel : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isLoggedIn = mutableStateOf(false)
}

