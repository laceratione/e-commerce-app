package com.example.domain.model

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object MySharedPref {
    private val namePref: String = "currentUser"
    private val modePref: Int = Context.MODE_PRIVATE

    fun saveCurrentUser(context: Context, email: String){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(namePref, modePref)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.apply()
    }

    fun getDataUser(application: Application, dataKey: String): String?{
        val sharedPreferences = application.getSharedPreferences(namePref, modePref)
        val email: String? = sharedPreferences.getString(dataKey, "error_data")
        return email
    }

    fun clear(application: Application){
        val sharedPreferences = application.getSharedPreferences(namePref, modePref)
        val editor = sharedPreferences.edit()
        editor.clear()
    }
}