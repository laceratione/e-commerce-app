package com.example.effectivemobiletest.presentation.ui.homepage

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.*
import com.example.domain.usecase.GetDataCart
import com.example.domain.usecase.GetDataHomeAct
import com.example.effectivemobiletest.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.net.URL
import javax.inject.Inject

class HomeViewModel(application: Application) : ViewModel() {
    //товары категории Hot Sale
    private val _dataHotProducts: MutableLiveData<List<HotProduct>> = MutableLiveData()
    val dataHotProducts: LiveData<List<HotProduct>> = _dataHotProducts

    //товары категории Best Seller
    private val _dataBestProducts: MutableLiveData<List<BestProduct>> = MutableLiveData()
    val dataBestProducts: LiveData<List<BestProduct>> = _dataBestProducts

    //товары корзины
    private val _dataMyCart: MutableLiveData<MyCart> = MutableLiveData()
    val dataMyCart: LiveData<MyCart> = _dataMyCart

    //проверка процесса загрузки товаров
    private val _isHotSalesLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isHotSalesLoading: LiveData<Boolean> = _isHotSalesLoading

    private val _isBestSellerLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isBestSellerLoading: LiveData<Boolean> = _isBestSellerLoading

    @Inject
    lateinit var popUpWindow: FilterSettings

    @Inject
    lateinit var dataHomeActUseCase: GetDataHomeAct

    @Inject
    lateinit var dataCartUseCase: GetDataCart

    //загрузка данных с сервера
    init {
        (application as App).appComponent.inject(this)

        viewModelScope.launch(Dispatchers.IO) {
            getDataHotSales()
        }

        viewModelScope.launch(Dispatchers.IO) {
            getDataMyCart()
        }
    }

    //загрузка товаров категорий Hot Sales, Best Seller
    suspend fun getDataHotSales() = coroutineScope {
        launch {
            _isHotSalesLoading.postValue(true)
            _isBestSellerLoading.postValue(true)

            dataHomeActUseCase().enqueue(object : Callback<Product> {
                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    val hotProducts = response.body()?.hotProducts
                    val bestProducts = response.body()?.bestProducts
                    updateData(hotProducts, bestProducts)
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    Log.d("myLogs", t.message.toString())
                }
            })
        }
    }

    private fun updateData(hotProducts: List<HotProduct>?, bestProducts: List<BestProduct>?){
        viewModelScope.launch(Dispatchers.IO) {
            hotProducts?.let {
                getBitmaps(it)
                _dataHotProducts.postValue(it)
            }
            _isHotSalesLoading.postValue(false)

            bestProducts?.let{
                getBitmaps(it)
                _dataBestProducts.postValue(it)
            }
            _isBestSellerLoading.postValue(false)
        }
    }

    //загрузка товаров из корзины пользователя
    suspend fun getDataMyCart() = coroutineScope {
        launch {
            dataCartUseCase().enqueue(object : Callback<MyCart> {
                override fun onResponse(call: Call<MyCart>, response: Response<MyCart>) {
                    val myCart = response.body()
                    viewModelScope.launch(Dispatchers.IO) {
                        myCart?.let {
                            getBitmaps(it.basket)
                            _dataMyCart.postValue(it)
                            Cart.updateCart(it)
                        }
                    }
                }

                override fun onFailure(call: Call<MyCart>, t: Throwable) {
                    Log.d("myLogs", t.message.toString())
                }
            })
        }
    }

    //ссылки URL преобразуем в Bitmap
    suspend fun getBitmaps(items: List<BaseProduct>) = coroutineScope {
        launch(Dispatchers.IO) {
            for (item in items) {
                try {
                    val picture = URL(item.picture).openStream()
                    val bitmap: Bitmap = BitmapFactory.decodeStream(picture)
                    item.bitmap = bitmap
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    //показать настройки фильтра
    fun showFilterSettings(view: View) {
        popUpWindow.showPopUpWindow(view)
    }
}