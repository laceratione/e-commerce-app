package com.example.effectivemobiletest.di

import android.content.Context
import androidx.room.Room
import com.example.data.AppDataBase
import com.example.data.MySharedPref
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(private val contex: Context) {

    @Provides
    fun provideDatabase(): AppDataBase {
        return Room.databaseBuilder(contex, AppDataBase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideSharedPref(): MySharedPref {
        return MySharedPref(contex)
    }
}