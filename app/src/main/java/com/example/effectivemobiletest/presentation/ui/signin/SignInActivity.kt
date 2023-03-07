package com.example.effectivemobiletest.presentation.ui.signin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.domain.model.MySharedPref
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ActivitySignInBinding
import com.example.effectivemobiletest.presentation.ui.homepage.MainActivity
import com.example.effectivemobiletest.presentation.ui.login.LoginActivity
import kotlin.math.sign

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
                    MySharedPref.saveCurrentUser(this, signInViewModel.email)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        })

        signInViewModel.message.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }


}