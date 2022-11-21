package com.example.effectivemobiletest.presentation.ui.productdetails

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.repository.CloudRepositoryImpl
import com.example.domain.usecase.GetDataDetails
import com.example.effectivemobiletest.App
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.net.URL
import javax.inject.Inject

class ProductDetailsViewModel(application: Application) : ViewModel() {
    //подробная информация товара
    val dataProductDetails: MutableLiveData<com.example.domain.model.ProductDetails> = MutableLiveData()

    //проверка загрузки данных
    val isProdDetLoading: MutableLiveData<Boolean> = MutableLiveData()

    @Inject
    lateinit var cloudRepositoryImpl: CloudRepositoryImpl

    //загрузка данных с сервера
    init {
        (application as App).appComponent.injectProductDetailsViewModel(this)

        val jobGetDetails: Job = GlobalScope.launch(Dispatchers.IO) {
            getDataDetails()
        }
        jobGetDetails.start()
    }

    suspend fun getDataDetails() = coroutineScope {
        launch {
            isProdDetLoading.postValue(true)

            val getDataDetails = GetDataDetails(cloudRepositoryImpl)
            getDataDetails().enqueue(object : Callback<com.example.domain.model.ProductDetails> {
                override fun onResponse(call: Call<com.example.domain.model.ProductDetails>, response: Response<com.example.domain.model.ProductDetails>) {
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

                override fun onFailure(call: Call<com.example.domain.model.ProductDetails>, t: Throwable) {
                    Log.d("myLogs", t.message.toString())
                }
            })

        }
    }

    //добавить в корзину
    fun addToCart() {}

}