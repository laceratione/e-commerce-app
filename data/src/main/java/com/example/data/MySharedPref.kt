package com.example.data

import android.content.Context

class MySharedPref(context: Context) {
    private val namePref: String = "currentUser"
    private val modePref: Int = Context.MODE_PRIVATE
    private val sharedPref = context.getSharedPreferences(namePref, modePref)

    fun setCurrentUser(email: String){
        sharedPref.edit().putString("email", email).apply()
    }

    fun getCurrentUser(): String?{
        return sharedPref.getString("email", null)
    }

    fun clear(){
        sharedPref.edit().clear().apply()
    }
}