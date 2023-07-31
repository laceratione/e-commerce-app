package com.example.effectivemobiletest.presentation.ui.productdetails

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ProductDetails
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
    private val _dataProductDetails: MutableLiveData<ProductDetails> = MutableLiveData()
    val dataProductDetails: LiveData<ProductDetails> = _dataProductDetails

    //проверка загрузки данных
    private val _isProdDetLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isProdDetLoading: LiveData<Boolean> = _isProdDetLoading

    @Inject
    lateinit var dataDetailsUseCase: GetDataDetails

    //загрузка данных с сервера
    init {
        (application as App).appComponent.inject(this)

        viewModelScope.launch(Dispatchers.IO) {
            getDataDetails()
        }
    }

    suspend fun getDataDetails() = coroutineScope {
        launch {
            _isProdDetLoading.postValue(true)
            dataDetailsUseCase().enqueue(object : Callback<ProductDetails> {
                override fun onResponse(call: Call<ProductDetails>, response: Response<ProductDetails>) {
                    val productDetails = response.body()
                    val bitmaps: MutableList<Bitmap> = mutableListOf()
                    viewModelScope.launch(Dispatchers.IO) {
                        if (productDetails != null) {
                            for (item in productDetails.images) {
                                try {
                                    val picture = URL(item).openStream()
                                    val bitmap: Bitmap = BitmapFactory.decodeStream(picture)
                                    bitmaps.add(bitmap)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                            productDetails.bitmaps = bitmaps
                            _dataProductDetails.postValue(productDetails)
                            _isProdDetLoading.postValue(false)
                        }
                    }
                }

                override fun onFailure(call: Call<ProductDetails>, t: Throwable) {
                    Log.d("myLogs", t.message.toString())
                }
            })

        }
    }
}