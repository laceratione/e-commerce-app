package com.example.effectivemobiletest.presentation.ui.productdetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

//адаптер для ViewPager экрана ProductDetails
class DetailsPageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private val COUNT_PAGE = 3
    override fun getItemCount(): Int {
        return COUNT_PAGE
    }

    override fun createFragment(position: Int): Fragment {
        return ShopFragment.newInstance(position + 1)
    }


}