package com.example.domain.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(UserEntity::class), version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao
}