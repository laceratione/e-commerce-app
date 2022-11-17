package com.example.effectivemobiletest.presentation.ui.productdetails

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletest.R
import java.lang.Exception
import android.graphics.drawable.shapes.RoundRectShape

//адаптер для размеров памяти товара
class CapacityAdapter(
) : RecyclerView.Adapter<CapacityAdapter.ViewHolder>() {
    private var index: Int = 0
    private var items: List<String>? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recview_capacity_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.capacity.text = items?.get(position) + " GB"
            holder.linearLayout.setOnClickListener {
                index = position
                notifyDataSetChanged()
            }

            //Color.parseColor("#FF6E4E")
            if (index == position) {
                setColor(holder, R.color.myColor1, R.color.white)
            }
            else{
                setColor(holder, R.color.white, R.color.gray)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        var count = items?.size ?: 0
        return count
    }

    fun updateItems(data: List<String>?) {
        data?.let { items = it }
        notifyDataSetChanged()
    }

    //задает цвет фона и текста
    private fun setColor(holder: ViewHolder, colorBack: Int, colorText: Int){
        val context = holder.linearLayout.context

        //форма элемента
        val r = 35f
        val draw = ShapeDrawable(
            RoundRectShape(
                floatArrayOf(r, r, r, r, r, r, r, r),
                null,
                null
            ))
        draw.paint.color = context.resources.getColor(colorBack)

        holder.linearLayout.background = draw
        holder.capacity.setTextColor(context.resources.getColor(colorText))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val capacity: TextView = itemView.findViewById(R.id.tvCapacity)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.llCapacity)
    }
}