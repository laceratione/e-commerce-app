package com.example.effectivemobiletest.presentation.ui.mycart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.FragmentMyCartBinding

class MyCartFragment: Fragment() {
    private val sharedViewModel: MyCartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMyCartBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_cart, container, false)
        binding.apply {
            myCartViewModel = sharedViewModel
        }
        binding.setLifecycleOwner(this)

        return binding.root
    }
}