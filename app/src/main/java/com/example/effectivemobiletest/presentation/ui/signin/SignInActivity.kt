package com.example.effectivemobiletest.presentation.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.effectivemobiletest.databinding.ActivitySignInBinding
import com.example.effectivemobiletest.presentation.ui.homepage.MainActivity
import com.example.effectivemobiletest.presentation.ui.login.LoginActivity

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivitySignInBinding.inflate(layoutInflater)
        val signInViewModel: SignInViewModel =
            ViewModelProvider(this, SignInViewModelFactory(application))
                .get(SignInViewModel::class.java)

        binding.btnSignin.setOnClickListener {
            signInViewModel.signinJob(
                binding.etFirstName.text.toString(),
                binding.etLastName.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.tvLogin.setOnClickListener {
            signInViewModel.login()
        }

        signInViewModel.action.observe(this, {
            when (it) {
                Action.NavigateToLogin -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                Action.NavigateToHomePage -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        })

        signInViewModel.message.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        setContentView(binding.root)
    }
}