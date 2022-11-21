package com.example.effectivemobiletest.presentation.ui.homepage

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletest.R
import com.example.domain.model.ItemCateg
import java.lang.Exception

//адаптер списка категорий (Books, Computers etc)
class RecViewCategoriesAdapter(
    val context: Context
) : RecyclerView.Adapter<RecViewCategoriesAdapter.ViewHolder>() {
    private var index: Int = -1
    private val icons: List<Int> = listOf(
        R.drawable.ic_action_book,
        R.drawable.ic_action_computer,
        R.drawable.ic_action_health,
        R.drawable.ic_action_phone,
        R.drawable.ic_action_tools,
    )

    //возвращает название категории
    private fun getTitle(i: Int): String {
        return context.resources.getStringArray(R.array.select_categories)[i]
    }

    public interface OnItemClickListener {
        fun onItemClick(itemCateg: ItemCateg, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recview_categories_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.icon.setImageResource(icons[position])
            holder.title.setText(getTitle(position))

            holder.linearLayout.setOnClickListener {
                index = position
                notifyDataSetChanged()
            }

            if (index == position) {
                setColor(holder, R.color.myColor1, R.color.white, R.color.myColor1)
            } else {
                setColor(holder, R.color.white, R.color.gray_shade2, R.color.black)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return icons.size
    }

    //задает форму элемента категории
    private fun getShape(backColor: Int): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(context.resources.getColor(backColor))
        gradientDrawable.shape = GradientDrawable.OVAL
        return gradientDrawable
    }

    //задает цвет фона, иконки и названия категории
    private fun setColor(holder: ViewHolder, backColor: Int, iconColor: Int, textColor: Int) {
        holder.icon.background = getShape(backColor)
        holder.icon.setColorFilter(
            ContextCompat.getColor(context, iconColor),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        holder.title.setTextColor(
            ContextCompat.getColor(context, textColor)
        )
    }

    class ViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvCategoriesItem)
        val icon: ImageView = view.findViewById(R.id.ivCategoriesItem)
        val linearLayout: LinearLayout = view.findViewById(R.id.llItemCateg)
    }
}