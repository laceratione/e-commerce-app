package com.example.data.repository

import com.example.data.AppDataBase
import com.example.data.user.UserEntity
import com.example.data.user.toData
import com.example.domain.model.User
import com.example.domain.repository.LocalUserRepository

//реализация локального хранилища для пользователей
class LocalUserRepositoryImpl(private val database: AppDataBase): LocalUserRepository {
    //добавить пользователя
    override fun addUser(user: User) {
        database.userDao().insertUser(user.toData())
    }

    //удалить пользователя
    override fun deleteUser(user: User) {
        database.userDao().deleteUser(user.toData())
    }

    //получить всех пользователей
    override fun getAllUsers(): List<User> {
        return database.userDao().getAll().map { it.toDomain() }
    }

    override fun getUserById(id: Int): User? {
        return database.userDao().getUserById(id)?.toDomain()
    }

    //получить пользователя по email
    override fun getUserByEmail(email: String): User? {
        return database.userDao().getUserByEmail(email)?.toDomain()
    }

    //получить пользователя по имени
    override fun getUserByFirstName(name: String): User? {
        return database.userDao().getUserByFirstName(name)?.toDomain()
    }

    //получить фото пользователя
    override fun getUserImage(email: String): String? {
        return database.userDao().getUserImage(email)
    }

    //обновить данные пользователя
    override fun updateUser(user: User) {
        database.userDao().updateUser(user.toData())
    }


}