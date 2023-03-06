package com.example.effectivemobiletest.presentation.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    private val sharedViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val recViewCategoriesAdapter = RecViewCategoriesAdapter(inflater.context)
        binding.apply {
            homeViewModel = sharedViewModel
            selectCategoryAdapter = recViewCategoriesAdapter
        }
        binding.setLifecycleOwner(this)

        return binding.root
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}