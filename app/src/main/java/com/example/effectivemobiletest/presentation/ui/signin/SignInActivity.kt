package com.example.effectivemobiletest.presentation.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ActivitySignInBinding
import com.example.effectivemobiletest.presentation.ui.homepage.HomeViewModelFactory
import com.example.effectivemobiletest.presentation.ui.homepage.MainActivity
import com.example.effectivemobiletest.presentation.ui.login.LoginActivity
import com.example.effectivemobiletest.presentation.ui.mycart.MyCartActivity

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.hide()

        val binding: ActivitySignInBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        val signInViewModel: SignInViewModel = ViewModelProvider(this, SignInViewModelFactory(application)).get(SignInViewModel::class.java)
        binding.signInViewModel = signInViewModel
        binding.lifecycleOwner = this

        signInViewModel.action.observe(this, {
            when(it){
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


    }
}