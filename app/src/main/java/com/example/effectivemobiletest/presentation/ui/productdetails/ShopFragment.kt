package com.example.effectivemobiletest.presentation.ui.productdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ShopFragmentBinding

//вкладка Shop экрана ProductDetails
class ShopFragment : Fragment() {
    lateinit var vm: ProductDetailsViewModel
    var pageNumber: Int = 0

    companion object {
        private const val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): ShopFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = ShopFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pageNumber = it.getInt(ARG_PAGE)
        }
        vm =
            ViewModelProvider(requireActivity()).get(ProductDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ShopFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.shop_fragment, container, false
        )
        binding.prodDetViewModel = vm
        binding.setLifecycleOwner (this)

        return binding.root
    }
}