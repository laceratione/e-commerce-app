package com.example.effectivemobiletest.presentation.ui.signin

import android.app.Application
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.User
import com.example.domain.model.UserEntity
import com.example.domain.repository.LocalUserRepository
import com.example.effectivemobiletest.App
import kotlinx.coroutines.*
import javax.inject.Inject

class SignInViewModel(val application: Application): ViewModel() {
    var firstName: String = ""
    var lastName: String = ""
    var email: String = ""
    var password: String = ""
    val formIsValid: MutableLiveData<Boolean> = MutableLiveData()

    private val _action = MutableLiveData<Action>()
    val action: LiveData<Action> = _action

    @Inject
    lateinit var localUserRepository: LocalUserRepository

    init {
        (application as App).appComponent.inject(this)
    }

    //перейти на экран Login
    fun login(){
        _action.value = Action.NavigateToLogin
    }

    //регистрауия пользователя
    suspend fun signin() = coroutineScope {
        launch {
            //проверка корректности полей
            val emailIsValid: Boolean =
                !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            val passwordIsValid: Boolean = !password.isEmpty() && password.length >= 6
            //тут какой-то баг
            formIsValid.postValue(!firstName.isEmpty() && !lastName.isEmpty()
                    && emailIsValid && passwordIsValid)

            if (formIsValid.value == false){
                Log.d("myLogs", "ДАННЫЕ НЕ КОРРЕКТНЫ")
                return@launch
            }
            //проверка на уже существующего пользователя
            val alreadyUserHave: UserEntity? = localUserRepository.getUserByEmail(email)
            alreadyUserHave?.let {
                Log.d("myLogs", "ТАКОЙ ПОЛЬЗОВАТЕЛЬ УЖЕ СУЩЕСТВУЕТ")
                return@launch
            }
            //запись данных пользователя в БД
            localUserRepository.addUser(UserEntity(0, firstName, lastName, email, password))
            _action.postValue(Action.NavigateToHomePage)
            Log.d("myLogs", "РЕГИСТРАЦИЯ ПРОШЛА УСПЕШНО")
        }

    }

    fun signInJob(){
        val jobSignIn = GlobalScope.launch(Dispatchers.IO){
            signin()
        }.start()
    }
}

enum class Action{
    NavigateToLogin, NavigateToHomePage,
}
