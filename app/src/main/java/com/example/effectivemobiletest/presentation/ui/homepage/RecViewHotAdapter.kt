package com.example.effectivemobiletest.presentation.ui.homepage

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletest.R
import java.lang.Exception

//адаптер списка товаров Hot Sales
class RecViewHotAdapter (
) : RecyclerView.Adapter<RecViewHotAdapter.ViewHolder>() {
    private var hotProducts: List<com.example.domain.model.HotProduct> = emptyList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recview_hot_sales_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val bitmap: Bitmap? = hotProducts[position].bitmap

            holder.ivPicture.setImageBitmap(bitmap)
            holder.tvTitle.text = hotProducts[position].title
            holder.tvSubtitle.text = hotProducts[position].subtitle

            if(hotProducts[position].isNew == true)
                holder.llNew.visibility = View.VISIBLE

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return hotProducts.size
    }

    fun updateItems(items: List<com.example.domain.model.HotProduct>?) {
        hotProducts = items ?: emptyList()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPicture: ImageView = itemView.findViewById(R.id.ivHotSales)
        val tvTitle: TextView = itemView.findViewById(R.id.tvModelHotSales)
        val tvSubtitle: TextView = itemView.findViewById(R.id.tvDescHotSales)
        val llNew: LinearLayout = itemView.findViewById(R.id.llNew)
    }
}