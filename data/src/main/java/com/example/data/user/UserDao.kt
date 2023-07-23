package com.example.data.user

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: com.example.data.user.UserEntity)

    @Delete
    fun deleteUser(user: com.example.data.user.UserEntity)

    @Query("select * from user_table")
    fun getAll(): List<com.example.data.user.UserEntity>

    @Query("select * from user_table where id = :id")
    fun getUserById(id: Int): com.example.data.user.UserEntity?

    @Query("select * from user_table where email = :email")
    fun getUserByEmail(email: String): com.example.data.user.UserEntity?

    @Query("select * from user_table where first_name = :name")
    fun getUserByFirstName(name: String): com.example.data.user.UserEntity?

    @Query("select imagePath from user_table WHERE email=:email")
    fun getUserImage(email: String): String?

    @Update
    fun updateUser(userEntity: com.example.data.user.UserEntity)
}