package com.example.reservation.data.Repository

import com.example.reservation.data.Model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UserRepository {
    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user

    fun saveUser(newUser: User) {
        _user.value = newUser
    }
}
