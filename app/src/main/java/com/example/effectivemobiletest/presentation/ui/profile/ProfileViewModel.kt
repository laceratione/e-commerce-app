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
import com.example.effectivemobiletest.presentation.ui.signin.Action
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel(val application: Application) : ViewModel() {
    val _action = MutableLiveData<Action>()

    private val _uriPhoto = MutableLiveData<String>()
    val uriPhoto: LiveData<String> = _uriPhoto

    @Inject
    lateinit var localUserRepository: LocalUserRepository

    @Inject
    lateinit var mySharedPref: MySharedPref

    init {
        (application as App).appComponent.inject(this)
        val loadData = viewModelScope.launch(Dispatchers.IO) {
            getUserPhoto()
        }.start()
    }

    //выход из учетной записи
    fun logout() {
        mySharedPref.clear()
        _action.postValue(Action.NavigateToSignIn)
    }

    fun setUserPhotoJob(uri: String) {
        viewModelScope.launch(Dispatchers.IO) { setUserPhoto(uri) }.start()
    }

    //изменить фото профиля
//    fun changePhoto() {
//        _action.value = Action.ChangePhoto
//    }

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
            val user: UserEntity? = localUserRepository.getUserByEmail(email!!)?.toData()
            Log.d("myLogs", "URI get: ${user?.imagePath}")
            localUserRepository.getUserImage(email!!)?.let { _uriPhoto.postValue(it) }
        }
    }


}
