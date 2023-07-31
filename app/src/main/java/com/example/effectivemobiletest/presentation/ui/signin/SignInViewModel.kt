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

    //регистрауия пользователя
    suspend fun signin(
        firstName: String, lastName: String, email: String, password: String
    ) = coroutineScope {
        launch {
            if (checkValidateForm(firstName, lastName, email, password) == false) {
                _message.postValue("Данные некорректны")
                //тут можно конкретизировать ошибку
                return@launch
            }

            //проверка на уже существующего пользователя
            val alreadyUserHave: UserEntity? = localUserRepository.getUserByEmail(email)?.toData()
            alreadyUserHave?.let {
                _message.postValue("Такой пользователь уже существует")
                return@launch
            }

            val user = UserEntity(0, firstName, lastName, email, password, null)
            addUserDB(user)
        }
    }

    //проверка корректности полей
    private fun checkValidateForm
                (firstName: String, lastName: String, email: String, password: String): Boolean{
        val emailIsValid: Boolean =
            !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordIsValid: Boolean = !password.isEmpty() && password.length >= 6
        val formIsValid: Boolean = !firstName.isEmpty() && !lastName.isEmpty()
                && emailIsValid && passwordIsValid
        return formIsValid
    }

    //запись данных пользователя в БД
    private fun addUserDB(user: UserEntity){
        localUserRepository.addUser(user.toDomain())
        mySharedPref.setCurrentUser(user.email)
        _message.postValue("Регистрация прошла успешно")
        _action.postValue(Action.NavigateToHomePage)
    }

    fun signinJob(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            signin(firstName, lastName, email, password)
        }
    }
}

enum class Action {
    NavigateToLogin, NavigateToHomePage, NavigateToSignIn, Default,
}