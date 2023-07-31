package com.example.effectivemobiletest.presentation.ui.mycart

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.MyCart
import com.example.effectivemobiletest.R
import java.lang.Exception

//адаптер списка элементов корзины
class RecViewCartAdapter(
) : RecyclerView.Adapter<RecViewCartAdapter.ViewHolder>() {
    private var myCart: MyCart? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recview_my_cart_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val bitmap: Bitmap? = myCart?.basket?.get(position)?.bitmap
            with(holder){
                picture.setImageBitmap(bitmap)
                title.text = myCart?.basket?.get(position)?.title
                price.text = "$" + myCart?.basket?.get(position)?.price.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        val count = myCart?.basket?.size ?: 0
        return count
    }

    fun updateItems(item: MyCart?) {
        item?.let { myCart = it }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val picture: ImageView = itemView.findViewById(R.id.ivItemCart)
        val title: TextView = itemView.findViewById(R.id.titleItemCart)
        val price: TextView = itemView.findViewById(R.id.priceItemCart)
    }
}