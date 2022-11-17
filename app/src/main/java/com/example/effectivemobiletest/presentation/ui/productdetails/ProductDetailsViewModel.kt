package com.example.effectivemobiletest.presentation.ui.productdetails

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.RadioGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.effectivemobiletest.data.RetrofitAPI
import com.example.effectivemobiletest.data.ServerAPI
import com.example.effectivemobiletest.domain.models.ProductDetails
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.net.URL

class ProductDetailsViewModel : ViewModel() {
    //подробная информация товара
    val dataProductDetails: MutableLiveData<ProductDetails> = MutableLiveData()

    //проверка загрузки данных
    val isProdDetLoading: MutableLiveData<Boolean> = MutableLiveData()

    //загрузка данных с сервера
    init {
        val jobGetDetails: Job = GlobalScope.launch(Dispatchers.IO) {
            getDataDetails()
        }
        jobGetDetails.start()
    }

    suspend fun getDataDetails() = coroutineScope {
        launch {
            isProdDetLoading.postValue(true)
            ServerAPI.getInstance()
                .create(RetrofitAPI::class.java)
                .getProductDetails()
                .enqueue(object : Callback<ProductDetails> {
                    override fun onResponse(
                        call: Call<ProductDetails>,
                        response: Response<ProductDetails>
                    ) {
                        val productDetails = response.body()
                        val bitmaps: MutableList<Bitmap> = mutableListOf()
                        GlobalScope.launch(Dispatchers.IO) {
                            if (productDetails != null) {
                                for (item in productDetails.images) {
                                    try {
                                        val picture = URL(item).openStream()
                                        var bitmap: Bitmap = BitmapFactory.decodeStream(picture)
                                        bitmaps.add(bitmap)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                                productDetails.bitmaps = bitmaps
                                dataProductDetails.postValue(productDetails)
                                isProdDetLoading.postValue(false)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ProductDetails>, t: Throwable) {
                        Log.d("myLogs", t.message.toString())
                    }
                })
        }
    }

    //добавить в корзину
    fun addToCart() {}


}