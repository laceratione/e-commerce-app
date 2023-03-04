package com.example.domain.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)

    @Query("select * from user_table")
    fun getAll(): List<UserEntity>

    @Query("select * from user_table where email = :email")
    fun getUserByEmail(email: String): UserEntity?

    @Query("select * from user_table where first_name = :name")
    fun getUserByFirstName(name: String): UserEntity?
}