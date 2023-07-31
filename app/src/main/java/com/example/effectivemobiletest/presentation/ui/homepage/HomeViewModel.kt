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

//    //выбранная страница навигации
//    private val _botNavPage: MutableLiveData<Int> = MutableLiveData()
//    val botNavPage: LiveData<Int> = _botNavPage

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
                    viewModelScope.launch(Dispatchers.IO) {
                        getBitmaps(hotProducts!!)
                        _dataHotProducts.postValue(hotProducts)
                        _isHotSalesLoading.postValue(false)

                        getBitmaps(bestProducts!!)
                        _dataBestProducts.postValue(bestProducts)
                        _isBestSellerLoading.postValue(false)
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    Log.d("myLogs", t.message.toString())
                }
            })

        }
    }

    //загрузка товаров из корзины пользователя
    suspend fun getDataMyCart() = coroutineScope {
        launch {
            dataCartUseCase().enqueue(object : Callback<MyCart> {
                override fun onResponse(call: Call<MyCart>, response: Response<MyCart>) {
                    val myCart = response.body()
                    viewModelScope.launch(Dispatchers.IO) {
                        if (myCart != null) {
                            getBitmaps(myCart.basket)
                            _dataMyCart.postValue(myCart)
                            Cart.updateCart(myCart)
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
        launch {
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

//    //возвращает индекс выбранной страницы
//    fun bottomNavItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.page_1 -> botNavPage.value = 1
//            R.id.page_2 -> botNavPage.value = 2
//            R.id.page_3 -> botNavPage.value = 3
//            R.id.page_4 -> botNavPage.value = 4
//        }
//
//        return true
//    }
    
}