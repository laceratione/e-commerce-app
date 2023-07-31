package com.example.effectivemobiletest.presentation.ui.homepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.effectivemobiletest.*
import com.example.effectivemobiletest.databinding.ActivityMainBinding
import com.example.domain.model.Cart
import com.example.effectivemobiletest.presentation.ui.mycart.MyCartFragment
import com.example.effectivemobiletest.presentation.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val homeViewModel: HomeViewModel =
            ViewModelProvider(this, HomeViewModelFactory(application))
                .get(HomeViewModel::class.java)

        loadFragment(HomeFragment.newInstance())

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            val fragment: Fragment
            when (menuItem.itemId) {
                R.id.page_1 -> {
                    fragment = HomeFragment()
                    loadFragment(fragment)
                }
                R.id.page_2 -> {
                    fragment = MyCartFragment()
                    loadFragment(fragment)
                }
                R.id.page_3 -> {}
                R.id.page_4 -> {
                    fragment = ProfileFragment()
                    loadFragment(fragment)
                }
            }
            return@setOnItemSelectedListener true
        }

        //отображение количества товаров в корзине на панели навигации
        Cart.dataLive.observe(this, {
            val botNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            val badge = botNav.getOrCreateBadge(R.id.page_2)
            badge.isVisible = true
            badge.number = it.basket.size
        })

        setContentView(binding.root)
    }

    //загрузка фрагмента
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}