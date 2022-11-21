package com.example.effectivemobiletest.presentation.ui.mycart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ActivityMyCartBinding

class MyCartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)
        supportActionBar?.hide()

        val binding: ActivityMyCartBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_my_cart)
        val myCartViewModel: MyCartViewModel = ViewModelProvider(this).get(MyCartViewModel::class.java)
        binding.myCartViewModel = myCartViewModel
        binding.lifecycleOwner = this
    }


}