package com.example.effectivemobiletest.presentation.ui.productdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ActivityProductDetailsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        supportActionBar?.hide()

        val binding: ActivityProductDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        val prodDetViewModel: ProductDetailsViewModel =
            ViewModelProvider(this, ProdDetViewModelFactory(application)).get(ProductDetailsViewModel::class.java)
        binding.prodDetViewModel = prodDetViewModel
        binding.setLifecycleOwner(this)

        //панель вкладок
        val pager: ViewPager2 = findViewById(R.id.pager)
        pager.adapter = DetailsPageAdapter(this)
        val tab: TabLayout = findViewById(R.id.tab)

        TabLayoutMediator(tab, pager) { tab, position ->
            tab.text = this.resources.getStringArray(R.array.tab_titles)[position]
        }.attach()


    }


}