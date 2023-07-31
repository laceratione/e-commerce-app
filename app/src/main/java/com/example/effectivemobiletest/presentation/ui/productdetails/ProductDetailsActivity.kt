package com.example.effectivemobiletest.presentation.ui.productdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ActivityProductDetailsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding =
            ActivityProductDetailsBinding.inflate(layoutInflater)
        val prodDetViewModel: ProductDetailsViewModel =
            ViewModelProvider(this, ProdDetViewModelFactory(application)).get(
                ProductDetailsViewModel::class.java
            )
        val productDetailsAdapter = ProductDetailsAdapter()

        binding.carouselRecyclerview.adapter = productDetailsAdapter

        prodDetViewModel.dataProductDetails.observe(this, { productDetails ->
            productDetailsAdapter.updateItems(productDetails)
            binding.title.text = productDetails.title
            binding.ratingBar.rating = productDetails.rating
        })

        prodDetViewModel.isProdDetLoading.observe(this, { isLoading ->
            binding.pbProdDet.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        //панель вкладок
        val pager: ViewPager2 = binding.pager
        pager.adapter = DetailsPageAdapter(this)
        val tab: TabLayout = binding.tab

        TabLayoutMediator(tab, pager) { tab, position ->
            tab.text = this.resources.getStringArray(R.array.tab_titles)[position]
        }.attach()

        setContentView(binding.root)
    }
}