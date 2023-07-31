package com.example.effectivemobiletest.presentation.ui.login

import android.app.Application
import android.util.Patterns
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

class LoginViewModel(val application: Application) : ViewModel() {
    private var user: UserEntity? = null

    private val _action = MutableLiveData<Action>()
    val action: LiveData<Action> = _action

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    //обернуть в usecase
    @Inject
    lateinit var localUserRepository: LocalUserRepository

    @Inject
    lateinit var mySharedPref: MySharedPref

    init {
        (application as App).appComponent.inject(this)
    }

    private suspend fun login(email: String, password: String) = coroutineScope {
        launch {
            //проверка корректности полей
            val emailIsValid: Boolean =
                !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            val formIsValid = emailIsValid && !password.isEmpty()

            if (formIsValid == false) {
                _message.postValue("Данные некорректны")
                return@launch
            }

            if (checkValidateUser(email, password) == false)
                _message.postValue("Данной учетной записи не существует")
        }
    }

    private fun checkValidateUser(email: String, password: String): Boolean{
        var result = false
        user = localUserRepository.getUserByEmail(email)?.toData()
        user?.let {
            if (password.equals(it.password)){
                mySharedPref.setCurrentUser(email)
                _message.postValue("Вход выполнен успешно")
                _action.postValue(Action.NavigateToHomePage)
            }else{
                _message.postValue("Неверный ввод пароля")
            }
            result = true
        }
        return result
    }

    fun loginJob(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            login(email, password)
        }
    }
}