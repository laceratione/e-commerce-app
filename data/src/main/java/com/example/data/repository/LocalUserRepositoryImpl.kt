package com.example.data.repository

import com.example.domain.model.AppDataBase
import com.example.domain.model.UserDao
import com.example.domain.model.UserEntity
import com.example.domain.repository.LocalUserRepository

//реализация локального хранилища для пользователей
class LocalUserRepositoryImpl(private val database: AppDataBase): LocalUserRepository {
    //добавить пользователя
    override fun addUser(userEntity: UserEntity) {
        database.userDao().insertUser(userEntity)
    }

    //удалить пользователя
    override fun deleteUser(userEntity: UserEntity) {
        database.userDao().deleteUser(userEntity)
    }

    //получить всех пользователей
    override fun getAllUsers(): List<UserEntity> {
        return database.userDao().getAll()
    }

    //получить пользователя по email
    override fun getUserByEmail(email: String): UserEntity? {
        return database.userDao().getUserByEmail(email)
    }

    //получить пользователя по имени
    override fun getUserByFirstName(name: String): UserEntity? {
        return database.userDao().getUserByFirstName(name)
    }

}