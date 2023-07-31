package com.example.domain.repository

import com.example.domain.model.User

interface LocalUserRepository {
    fun addUser(user: User)

    fun deleteUser(user: User)

    fun getAllUsers(): List<User>

    fun getUserById(id: Int): User?

    fun getUserByEmail(email: String): User?

    fun getUserByFirstName(name: String): User?

    fun getUserImage(email: String): String?

    fun updateUser(user: User)
}