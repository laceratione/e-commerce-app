package com.example.effectivemobiletest.presentation.ui.signin

import android.app.Application
import android.util.Log
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
import kotlinx.coroutines.*
import javax.inject.Inject

class SignInViewModel(val application: Application) : ViewModel() {
//    var firstName: String = ""
//    var lastName: String = ""
//    var email: String = ""
//    var password: String = ""

    private val _action = MutableLiveData<Action>()
    val action: LiveData<Action> = _action

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    //обернуть в use case
    @Inject
    lateinit var localUserRepository: LocalUserRepository

    @Inject
    lateinit var mySharedPref: MySharedPref

    init {
        (application as App).appComponent.inject(this)
    }

    //перейти на экран Login
    fun login() {
        _action.value = Action.NavigateToLogin
    }

    //декомпозировать метод
    //регистрауия пользователя
    suspend fun signin(firstName: String, lastName: String, email: String, password: String) = coroutineScope {
        launch {
            //проверка корректности полей
            val emailIsValid: Boolean =
                !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            val passwordIsValid: Boolean = !password.isEmpty() && password.length >= 6
            val formIsValid: Boolean = !firstName.isEmpty() && !lastName.isEmpty()
                    && emailIsValid && passwordIsValid

            if (formIsValid == false) {
                _message.postValue("Данные некорректны")
                //тут можно уточнить, что конкретно некорректно
                return@launch
            }
            //проверка на уже существующего пользователя
            val alreadyUserHave: UserEntity? = localUserRepository.getUserByEmail(email)?.toData()
            alreadyUserHave?.let {
                _message.postValue("Такой пользователь уже существует")
                return@launch
            }
            //запись данных пользователя в БД
            val user = UserEntity(0, firstName, lastName, email, password, null)
            localUserRepository.addUser(user.toDomain())
            mySharedPref.setCurrentUser(email)
            _message.postValue("Регистрация прошла успешно")
            _action.postValue(Action.NavigateToHomePage)

            Log.d("myLogs", "User: ${user.email}, ID: ${user.id}")
        }

    }

    fun signinJob(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            signin(firstName, lastName, email, password)
        }
    }
}

enum class Action {
    NavigateToLogin, NavigateToHomePage, NavigateToSignIn,
    ChangePhoto, Default,
}