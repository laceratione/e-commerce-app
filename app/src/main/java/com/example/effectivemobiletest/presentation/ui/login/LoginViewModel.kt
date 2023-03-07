package com.example.effectivemobiletest.presentation.ui.login

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.UserEntity
import com.example.domain.repository.LocalUserRepository
import com.example.effectivemobiletest.App
import com.example.effectivemobiletest.presentation.ui.signin.Action
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel(val application: Application) : ViewModel() {
    var email: String = ""
    var password: String = ""

    private val _action = MutableLiveData<Action>()
    val action: LiveData<Action> = _action

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    @Inject
    lateinit var localUserRepository: LocalUserRepository

    init {
        (application as App).appComponent.inject(this)
    }

    suspend fun login() = coroutineScope {
        launch {
            //проверка корректности полей
            val emailIsValid: Boolean =
                !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            val formIsValid = emailIsValid && !password.isEmpty()

            if (formIsValid == false) {
                _message.postValue("Данные некорректны")
                return@launch
            }

            //переход на главную страницу
            val user: UserEntity? = localUserRepository.getUserByEmail(email)
            user?.let {
                if (password.equals(it.password)){

                    _message.postValue("Вход выполнен успешно")
                    _action.postValue(Action.NavigateToHomePage)
                }else{
                    _message.postValue("Неверный ввод пароля")
                }
                return@launch
            }

            _message.postValue("Данной учетной записи не существует")
        }
    }

    fun logInJob() {
        val jobLogIn = GlobalScope.launch(Dispatchers.IO) {
            login()
        }.start()
    }

}