package com.example.effectivemobiletest.presentation.ui.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MySharedPref
import com.example.data.user.UserEntity
import com.example.data.user.toData
import com.example.domain.repository.LocalUserRepository
import com.example.effectivemobiletest.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel(val application: Application) : ViewModel() {
    private val _uriPhoto = MutableLiveData<String>()
    val uriPhoto: LiveData<String> = _uriPhoto

    //обернуть в use case
    @Inject
    lateinit var localUserRepository: LocalUserRepository

    @Inject
    lateinit var mySharedPref: MySharedPref

    init {
        (application as App).appComponent.inject(this)

        viewModelScope.launch(Dispatchers.IO) {
            getUserPhoto()
        }
    }

    //выход из учетной записи
    fun logout() {
        mySharedPref.clear()
    }

    fun setUserPhotoJob(uri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setUserPhoto(uri)
        }
    }

    fun loadImageFromGallery(uri: String) {
        _uriPhoto.value = uri
    }

    //сохранить фото профиля
    suspend fun setUserPhoto(uri: String) = coroutineScope {
        launch {
            val email: String? = mySharedPref.getCurrentUser()
            val user = localUserRepository.getUserByEmail(email!!)
            user?.let {
                it.imagePath = uri
                localUserRepository.updateUser(it)
            }
        }
    }

    //загрузить фото профиля
    suspend fun getUserPhoto() = coroutineScope {
        launch {
            val email: String? = mySharedPref.getCurrentUser()
            email?.let {
                val user: UserEntity? = localUserRepository.getUserByEmail(it)?.toData()
                Log.d("myLogs", "URI get: ${user?.imagePath}")
                localUserRepository.getUserImage(it)?.let { _uriPhoto.postValue(it) }
            }
        }
    }
}