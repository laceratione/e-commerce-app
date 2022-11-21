package com.example.effectivemobiletest.presentation.ui.homepage

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.repository.CloudRepositoryImpl
import com.example.domain.model.BestProduct
import com.example.domain.model.HotProduct
import com.example.domain.model.MyCart
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
    val dataHotProducts: MutableLiveData<List<HotProduct>> = MutableLiveData()

    //товары категории Best Seller
    val dataBestProducts: MutableLiveData<List<BestProduct>> = MutableLiveData()

    //товары корзины
    val dataMyCart: MutableLiveData<MyCart> = MutableLiveData()

    //проверка процесса загрузки товаров
    val isHotSalesLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isBestSellerLoading: MutableLiveData<Boolean> = MutableLiveData()

    //выбранная страница навигации
    val botNavPage: MutableLiveData<Int> = MutableLiveData()

    @Inject
    lateinit var popUpWindow: FilterSettings

    @Inject
    lateinit var cloudRepositoryImpl: CloudRepositoryImpl

    //загрузка данных с сервера
    init {
        (application as App).appComponent.injectHomeViewModel(this)

        val jobHotSales: Job = GlobalScope.launch(Dispatchers.IO) {
            getDataHotSales()
        }
        jobHotSales.start()

        val jobMyCart: Job = GlobalScope.launch(Dispatchers.IO) {
            getDataMyCart()
        }
        jobMyCart.start()
    }

    //загрузка товаров категорий Hot Sales, Best Seller
    suspend fun getDataHotSales() = coroutineScope {
        launch {
            isHotSalesLoading.postValue(true)
            isBestSellerLoading.postValue(true)

            val getDataHomeAct = GetDataHomeAct(cloudRepositoryImpl)
            getDataHomeAct().enqueue(object : Callback<com.example.domain.model.Product> {
                override fun onResponse(call: Call<com.example.domain.model.Product>, response: Response<com.example.domain.model.Product>) {
                    val hotProducts = response.body()?.hotProducts
                    val bestProducts = response.body()?.bestProducts
                    GlobalScope.launch(Dispatchers.IO) {
                        getBitmaps(hotProducts!!)
                        dataHotProducts.postValue(hotProducts)
                        isHotSalesLoading.postValue(false)

                        getBitmaps(bestProducts!!)
                        dataBestProducts.postValue(bestProducts)
                        isBestSellerLoading.postValue(false)
                    }
                }

                override fun onFailure(call: Call<com.example.domain.model.Product>, t: Throwable) {
                    Log.d("myLogs", t.message.toString())
                }
            })

        }
    }

    //загрузка товаров из корзины пользователя
    suspend fun getDataMyCart() = coroutineScope {
        launch {
            val getDataCart = GetDataCart(cloudRepositoryImpl)
            getDataCart().enqueue(object : Callback<com.example.domain.model.MyCart> {
                override fun onResponse(call: Call<com.example.domain.model.MyCart>, response: Response<com.example.domain.model.MyCart>) {
                    val myCart = response.body()
                    GlobalScope.launch(Dispatchers.IO) {
                        if (myCart != null) {
                            getBitmaps(myCart.basket)
                            dataMyCart.postValue(myCart)
                            com.example.domain.model.Cart.data.postValue(myCart)
                        }
                    }
                }

                override fun onFailure(call: Call<com.example.domain.model.MyCart>, t: Throwable) {
                    Log.d("myLogs", t.message.toString())
                }
            })

        }
    }

    //ссылки URL преобразуем в Bitmap
    suspend fun getBitmaps(items: List<com.example.domain.model.BaseProduct>) = coroutineScope {
        launch {
            for (item in items) {
                try {
                    val picture = URL(item.picture).openStream()
                    var bitmap: Bitmap = BitmapFactory.decodeStream(picture)
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

    //возвращает индекс выбранной страницы
    fun bottomNavItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.page_2 -> botNavPage.value = 2
        }

        return true
    }
    
}