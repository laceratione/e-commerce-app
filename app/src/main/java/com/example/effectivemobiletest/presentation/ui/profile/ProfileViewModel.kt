package com.example.effectivemobiletest.presentation.ui.profile

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.UserEntity
import com.example.domain.repository.LocalUserRepository
import com.example.effectivemobiletest.App
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.presentation.ui.signin.Action
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel(val application: Application): ViewModel() {
    private val _action = MutableLiveData<Action>()
    val action: LiveData<Action> = _action

    @Inject
    lateinit var localUserRepository: LocalUserRepository

    init {
        (application as App).appComponent.inject(this)
    }

    //выход из учетной записи
    suspend fun logout() = coroutineScope{
        launch {
            val sharedPreferences = application.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
            val email: String? = sharedPreferences.getString("email", "error_email")
            val user: UserEntity? = localUserRepository.getUserByEmail(email!!)
            user?.let {
                localUserRepository.deleteUser(it)
                val editor = sharedPreferences.edit()
                editor.clear()
            }
            _action.postValue(Action.NavigateToSignIn)
        }
    }

    fun logoutJob(){
        val jobLogout = GlobalScope.launch(Dispatchers.IO){
            logout()
        }.start()
    }

    //изменить фото профиля
    fun changePhoto(){
        _action.value = Action.ChangePhoto
    }
}
