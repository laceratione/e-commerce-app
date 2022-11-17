package com.example.effectivemobiletest.presentation.ui.homepage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.effectivemobiletest.*
import com.example.effectivemobiletest.data.RetrofitAPI
import com.example.effectivemobiletest.data.ServerAPI
import com.example.effectivemobiletest.domain.models.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.net.URL
import javax.inject.Inject

class HomeViewModel : ViewModel() {
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

    //загрузка данных с сервера
    init {
        (MainActivity.get().application as App).appComponent.injectHomeViewModel(this)

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

            ServerAPI.getInstance()
                .create(RetrofitAPI::class.java)
                .getObjectsHotSales()
                .enqueue(object : Callback<Product> {
                    override fun onResponse(call: Call<Product>, response: Response<Product>) {
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

                    override fun onFailure(call: Call<Product>, t: Throwable) {
                        Log.d("myLogs", t.message.toString())
                    }
                })
        }
    }

    //загрузка товаров из корзины пользователя
    suspend fun getDataMyCart() = coroutineScope {
        launch {
            ServerAPI.getInstance()
                .create(RetrofitAPI::class.java)
                .getMyCart()
                .enqueue(object : Callback<MyCart> {
                    override fun onResponse(
                        call: Call<MyCart>,
                        response: Response<MyCart>
                    ) {
                        val myCart = response.body()
                        GlobalScope.launch(Dispatchers.IO) {
                            if (myCart != null) {
                                getBitmaps(myCart.basket)
                                dataMyCart.postValue(myCart)
                                Cart.data.postValue(myCart)
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