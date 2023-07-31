package com.example.effectivemobiletest.presentation.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.effectivemobiletest.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val sharedViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesAdapter = RecViewCategoriesAdapter(requireContext())
        val hotSalesAdapter = RecViewHotAdapter()
        val bestSellerAdapter = GridViewBestAdapter(requireContext())

        with(binding) {
            recViewCategories.adapter = categoriesAdapter
            recViewHotSales.adapter = hotSalesAdapter
            gwBestSeller.adapter = bestSellerAdapter

            ivFilterSettings.setOnClickListener {
                sharedViewModel.showFilterSettings(view)
            }
        }

        with(sharedViewModel) {
            dataHotProducts.observe(viewLifecycleOwner, { hotProducts ->
                hotSalesAdapter.updateItems(hotProducts)
            })

            dataBestProducts.observe(viewLifecycleOwner, { bestProducts ->
                bestSellerAdapter.updateItems(bestProducts)
            })

            isHotSalesLoading.observe(viewLifecycleOwner, { isLoading ->
                binding.pbHotSales.visibility = if (isLoading) View.VISIBLE else View.GONE
            })

            isBestSellerLoading.observe(viewLifecycleOwner, { isLoading ->
                binding.pbBestSeller.visibility = if (isLoading) View.VISIBLE else View.GONE
            })
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}