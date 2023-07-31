package com.example.effectivemobiletest.presentation.ui.productdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ShopFragmentBinding

//вкладка Shop экрана ProductDetails
class ShopFragment : Fragment() {
    private lateinit var binding: ShopFragmentBinding
    private val sharedViewModel: ProductDetailsViewModel by activityViewModels()
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
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShopFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colorAdapter = ColorAdapter()
        val capacityAdapter = CapacityAdapter()
        binding.rvColor.adapter = colorAdapter
        binding.rvCapacity.adapter = capacityAdapter

        sharedViewModel.dataProductDetails.observe(viewLifecycleOwner, { productDetails ->
            with(binding) {
                titleCpu.text = productDetails.cpu
                titleCamera.text = productDetails.camera
                titleSsd.text = productDetails.ssd
                titleSd.text = productDetails.sd

                btnAddToCart.text = getString(
                    R.string.btn_add_to_cart,
                    productDetails.price.toString()
                )

                colorAdapter.updateItems(productDetails.color)
                capacityAdapter.updateItems(productDetails.capacity)
            }
        })
    }
}