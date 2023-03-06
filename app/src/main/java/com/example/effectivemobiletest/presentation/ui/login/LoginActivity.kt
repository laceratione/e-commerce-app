package com.example.effectivemobiletest.presentation.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ActivityLoginBinding
import com.example.effectivemobiletest.presentation.ui.homepage.MainActivity
import com.example.effectivemobiletest.presentation.ui.signin.Action

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val loginViewModel: LoginViewModel = ViewModelProvider(this, LoginViewModelFactory(application)).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        loginViewModel.action.observe(this, {
            when(it){
                Action.NavigateToHomePage -> {
                    saveCurrentUser(loginViewModel.email)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        })

        loginViewModel.message.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun saveCurrentUser(email: String){
        val sharedPreferences = getSharedPreferences("currentUser", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.commit()
    }
}