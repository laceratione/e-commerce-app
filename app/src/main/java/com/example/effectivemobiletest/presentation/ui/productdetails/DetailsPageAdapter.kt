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

//class DetailsPageAdapter(fragmentManager: FragmentManager, val context: Context): FragmentStateAdapter(fragmentManager) {
//    private val COUNT_PAGE = 3
//    private val titles: List<String> = listOf("Shop", "Details", "Features")
//
//    override fun getCount(): Int {
//        return COUNT_PAGE
//    }
//
//    override fun getItem(position: Int): Fragment {
//        return ShopFragment.newInstance(position + 1)
//    }
//
//    override fun getPageTitle(position: Int): CharSequence {
//        return titles[position]
//    }
//}