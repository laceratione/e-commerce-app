package com.example.effectivemobiletest.presentation.ui.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.effectivemobiletest.*
import com.example.effectivemobiletest.databinding.ActivityMainBinding
import com.example.domain.model.Cart
import com.example.effectivemobiletest.presentation.ui.mycart.MyCartActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val homeViewModel: HomeViewModel = ViewModelProvider(this, HomeViewModelFactory(application)).get(HomeViewModel::class.java)
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this

        val recViewCategoriesAdapter: RecViewCategoriesAdapter = RecViewCategoriesAdapter(this)
        binding.selectCategoryAdapter = recViewCategoriesAdapter

        //открытие Activity по навигации
        homeViewModel.botNavPage.observe(this, {
            when(it){
                2 -> {
                    val intent = Intent(this, MyCartActivity::class.java)
                    startActivity(intent)
                }
            }
        })

        //отображение количества товаров в корзине на панели навигации
        com.example.domain.model.Cart.data.observe(this, {
            val botNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            val badge = botNav.getOrCreateBadge(R.id.page_2)
            badge.isVisible = true
            badge.number = it.basket.size
        })
    }


}