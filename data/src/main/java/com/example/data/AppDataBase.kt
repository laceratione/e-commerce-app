package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.user.UserDao
import com.example.data.user.UserEntity

@Database(entities = arrayOf(UserEntity::class), version = 2)
abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao
}