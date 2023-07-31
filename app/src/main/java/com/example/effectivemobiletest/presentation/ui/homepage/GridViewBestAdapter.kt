package com.example.effectivemobiletest.presentation.ui.homepage

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.domain.model.BestProduct
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.presentation.ui.productdetails.ProductDetailsActivity

//адаптер товаров Best Seller
class GridViewBestAdapter (private val context: Context): BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private var bestProducts: List<BestProduct> = emptyList()

    override fun getCount(): Int {
        return bestProducts.size
    }

    override fun getItem(pos: Int): Any {
        return bestProducts[pos]
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
        var grid = convertView

        if (layoutInflater == null){
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                    as LayoutInflater
        }

        if (grid == null){
            grid = layoutInflater!!.inflate(R.layout.gridview_best_seller_item, null)
        }

        val picture: ImageView? = grid?.findViewById(R.id.ivBestSeller)
        val priceWithDiscount: TextView? = grid?.findViewById(R.id.tvPriceWithDiscountBestSeller)
        val priceWithoutDiscount: TextView? = grid?.findViewById(R.id.tvPriceWithoutDiscountBestSeller)
        val title: TextView? = grid?.findViewById(R.id.tvTitleBestSeller)
        val favorites: ImageView? = grid?.findViewById(R.id.favoritesGridItem)

        picture?.setImageBitmap(bestProducts[pos].bitmap)
        priceWithDiscount?.setText("$" + bestProducts[pos].discountPrice.toString())
        priceWithoutDiscount?.setText("$" + bestProducts[pos].priceWithoutDiscount.toString())
        priceWithoutDiscount?.setPaintFlags(priceWithoutDiscount.getPaintFlags() or (Paint.STRIKE_THRU_TEXT_FLAG))
        title?.setText(bestProducts[pos].title)

        //просмотр детальной информации товара
        grid?.setOnClickListener{
            val intent: Intent = Intent(parent?.context, ProductDetailsActivity::class.java)
            parent?.context?.startActivity(intent)
        }

        //добавить товар в избранное
        //реализовать "убрать из списка"
        favorites?.setOnClickListener{
            (it as ImageView).setImageResource(R.drawable.ic_favotite_fill)
            Toast.makeText(it.context, "Added to favorites", Toast.LENGTH_LONG).show()
        }
        
        return grid!!
    }

    fun updateItems(items: List<BestProduct>?) {
        bestProducts = items ?: emptyList()
        notifyDataSetChanged()
    }
}