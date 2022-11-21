package com.example.effectivemobiletest

import android.content.Context
import android.widget.GridView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletest.presentation.ui.homepage.GridViewBestAdapter
import com.example.effectivemobiletest.presentation.ui.homepage.RecViewHotAdapter
import com.example.effectivemobiletest.presentation.ui.mycart.RecViewCartAdapter
import com.example.effectivemobiletest.presentation.ui.productdetails.CapacityAdapter
import com.example.effectivemobiletest.presentation.ui.productdetails.ColorAdapter
import com.example.effectivemobiletest.presentation.ui.productdetails.ProductDetailsAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview

//обновление товаров категории Hot Sales
@BindingAdapter("dataHotProducts")
fun bindItemViewModels(recView: RecyclerView, itemViewModels: List<com.example.domain.model.HotProduct>?) {
    recView.layoutManager =
        LinearLayoutManager(recView.context, LinearLayoutManager.HORIZONTAL, false)
    val adapter = createAdapter(recView)
    adapter.updateItems(itemViewModels)
}

//создание RecViewHotAdapter
private fun createAdapter(recyclerView: RecyclerView): RecViewHotAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is RecViewHotAdapter) {
        recyclerView.adapter as RecViewHotAdapter
    } else {
        val bindableRecyclerAdapter = RecViewHotAdapter()
        recyclerView.adapter = bindableRecyclerAdapter
        bindableRecyclerAdapter
    }
}

//обновление товаров категории Best Sellet
@BindingAdapter("dataBestProducts", "myContext")
fun bindGridView(gridView: GridView, itemViewModels: List<com.example.domain.model.BestProduct>?, context: Context) {
    val adapter = createGridViewAdapter(gridView, context)
    adapter.updateItems(itemViewModels)
}

//создание GridViewBestAdapter
private fun createGridViewAdapter(gridView: GridView, context: Context): GridViewBestAdapter {
    return if (gridView.adapter != null && gridView.adapter is GridViewBestAdapter) {
        gridView.adapter as GridViewBestAdapter
    } else {
        val bindableGridViewAdapter = GridViewBestAdapter(context)
        gridView.adapter = bindableGridViewAdapter
        bindableGridViewAdapter
    }
}

//создание ProductDetailsAdapter
private fun createProdDetAdapter(recyclerView: RecyclerView): ProductDetailsAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is ProductDetailsAdapter) {
        recyclerView.adapter as ProductDetailsAdapter
    } else {
        val bindableRecyclerAdapter = ProductDetailsAdapter()
        recyclerView.adapter = bindableRecyclerAdapter
        bindableRecyclerAdapter
    }
}

//обновление товаров корзины
@BindingAdapter("dataCart")
fun bindMyCart(recView: RecyclerView, itemViewModels: com.example.domain.model.MyCart?) {
    recView.layoutManager =
        LinearLayoutManager(recView.context, LinearLayoutManager.VERTICAL, false)
    val adapter = createMyCartAdapter(recView)
    adapter.updateItems(itemViewModels)
}

//создание RecViewCartAdapter
private fun createMyCartAdapter(recyclerView: RecyclerView): RecViewCartAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is RecViewCartAdapter) {
        recyclerView.adapter as RecViewCartAdapter
    } else {
        val bindableRecyclerAdapter = RecViewCartAdapter()
        recyclerView.adapter = bindableRecyclerAdapter
        bindableRecyclerAdapter
    }
}

//переход по страницам главного экрана
@BindingAdapter("onNavigationItemSelected")
fun setOnNavigationItemSelectedListener(
    view: BottomNavigationView,
    listener: BottomNavigationView.OnNavigationItemSelectedListener
) {
    view.setOnNavigationItemSelectedListener(listener)
}

//получение изображений товара ProductDetails
@BindingAdapter("dataCarousel")
fun bindItemViewModels(recView: CarouselRecyclerview, itemViewModels: com.example.domain.model.ProductDetails?) {
    val adapter = createProdDetAdapter(recView)
    adapter.updateItems(itemViewModels)
}

//получение доступной объёма памяти товара
@BindingAdapter("dataCapacity")
fun bindCapacity(recView: RecyclerView, items: List<String>?) {
    recView.layoutManager =
        LinearLayoutManager(recView.context, LinearLayoutManager.HORIZONTAL, false)
    val adapter = createCapacityAdapter(recView)
    adapter.updateItems(items)
}

private fun createCapacityAdapter(recyclerView: RecyclerView): CapacityAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is RecViewCartAdapter) {
        recyclerView.adapter as CapacityAdapter
    } else {
        val bindableRecyclerAdapter = CapacityAdapter()
        recyclerView.adapter = bindableRecyclerAdapter
        bindableRecyclerAdapter
    }
}

//получение доступных цветов товара
@BindingAdapter("dataColor")
fun bindColor(recView: RecyclerView, items: List<String>?) {
    recView.layoutManager =
        LinearLayoutManager(recView.context, LinearLayoutManager.HORIZONTAL, false)
    val adapter = createColorAdapter(recView)
    adapter.updateItems(items)
}

private fun createColorAdapter(recyclerView: RecyclerView): ColorAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is ColorAdapter) {
        recyclerView.adapter as ColorAdapter
    } else {
        val bindableRecyclerAdapter = ColorAdapter()
        recyclerView.adapter = bindableRecyclerAdapter
        bindableRecyclerAdapter
    }
}