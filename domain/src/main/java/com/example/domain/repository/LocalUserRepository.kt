package com.example.domain.repository

import com.example.domain.model.UserEntity

interface LocalUserRepository {
    fun addUser(userEntity: UserEntity)
    fun deleteUser(userEntity: UserEntity)
    fun getAllUsers(): List<UserEntity>
    fun getUserByEmail(email: String): UserEntity?
}