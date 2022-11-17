package com.example.effectivemobiletest.presentation.ui.productdetails

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletest.R
import java.lang.Exception

class ColorAdapter(
) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {
    private var index: Int = 0
    private var items: List<String>? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recview_color_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.linearLayout.background.setTint(Color.parseColor(items?.get(position)))
            holder.linearLayout.setOnClickListener {
                index = position
                notifyDataSetChanged()
            }

            if (index == position) {
                holder.checkMark.visibility = View.VISIBLE
            } else {
                holder.checkMark.visibility = View.GONE
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkMark: ImageView = itemView.findViewById(R.id.ivColor)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.llColor)
    }
}