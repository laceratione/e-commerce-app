package com.example.effectivemobiletest.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.FragmentProfileBinding

class ProfileFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.setLifecycleOwner(this)

        return binding.root
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}