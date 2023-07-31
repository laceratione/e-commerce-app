package com.example.effectivemobiletest.presentation.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ActivityLoginBinding
import com.example.effectivemobiletest.presentation.ui.homepage.MainActivity
import com.example.effectivemobiletest.presentation.ui.signin.Action

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding =
            ActivityLoginBinding.inflate(layoutInflater)
        val loginViewModel: LoginViewModel =
            ViewModelProvider(this, LoginViewModelFactory(application))
                .get(LoginViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            loginViewModel.loginJob(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        loginViewModel.action.observe(this, {
            when(it){
                Action.NavigateToHomePage -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        })

        loginViewModel.message.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        setContentView(binding.root)
    }

}