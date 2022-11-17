package com.example.effectivemobiletest.presentation.ui.productdetails

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.domain.models.ProductDetails
import java.lang.Exception

//адаптер изображений ProductDetails
class ProductDetailsAdapter (
) : RecyclerView.Adapter<ProductDetailsAdapter.ViewHolder>() {
    private var productDetails: ProductDetails? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recview_product_details_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val bitmap: Bitmap? = productDetails?.bitmaps?.get(position)
            holder.ivPicture.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        var count = productDetails?.images?.size ?: 0
        return count
    }

    fun updateItems(item: ProductDetails?) {
        item?.let { productDetails = it }
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPicture: ImageView = itemView.findViewById(R.id.ivProductDetails)
    }
}