package com.example.effectivemobiletest.di

import android.content.Context
import androidx.room.Room
import com.example.domain.model.AppDataBase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(private val contex: Context) {

    @Provides
    fun provideDatabase(): AppDataBase {
        return Room.databaseBuilder(contex, AppDataBase::class.java, "database")
            .build()
    }
}