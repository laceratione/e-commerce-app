package com.example.effectivemobiletest.presentation.ui.homepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.effectivemobiletest.*
import com.example.effectivemobiletest.databinding.ActivityMainBinding
import com.example.domain.model.Cart
import com.example.effectivemobiletest.presentation.ui.mycart.MyCartFragment
import com.example.effectivemobiletest.presentation.ui.profile.ProfileFragment
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

        loadFragment(HomeFragment.newInstance())

        //открытие Activity по навигации
        homeViewModel.botNavPageLive.observe(this, {
            val fragment: Fragment
            when(it){
                1 -> {
                    fragment = HomeFragment()
                    loadFragment(fragment)
                }
                2 -> {
                    fragment = MyCartFragment()
                    loadFragment(fragment)
                }
                3 ->{}
                4 -> {
//                    getSupportActionBar()?.setTitle("Profle")
                    fragment = ProfileFragment()
                    loadFragment(fragment)
                }
            }
        })

        //отображение количества товаров в корзине на панели навигации
        Cart.dataLive.observe(this, {
            val botNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            val badge = botNav.getOrCreateBadge(R.id.page_2)
            badge.isVisible = true
            badge.number = it.basket.size
        })

    }

    //загрузка фрагмента
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}