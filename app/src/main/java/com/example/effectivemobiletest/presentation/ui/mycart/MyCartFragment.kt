package com.example.effectivemobiletest.presentation.ui.mycart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.effectivemobiletest.databinding.FragmentMyCartBinding

class MyCartFragment: Fragment() {
    private lateinit var binding: FragmentMyCartBinding
    private val sharedViewModel: MyCartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentMyCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recViewCartAdapter = RecViewCartAdapter()
        binding.recViewMyCart.adapter = recViewCartAdapter

        sharedViewModel.dataMyCart.observe(viewLifecycleOwner, { cart ->
            recViewCartAdapter.updateItems(cart)
            binding.tvTotalCartSumm.text = "$ ${cart.total}"
            binding.tvDeliveryValue.text = cart.delivery
        })
    }
}