package com.example.effectivemobiletest.presentation.ui.login

import android.app.Application
import android.util.Log
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
    var firstName: String = ""
    var password: String = ""

    private val _action = MutableLiveData<Action>()
    val action: LiveData<Action> = _action

    @Inject
    lateinit var localUserRepository: LocalUserRepository

    init {
        (application as App).appComponent.inject(this)
    }

    suspend fun login() = coroutineScope {
        launch {
            //проверка корректности полей
            val formIsValid = !firstName.isEmpty() && !password.isEmpty()

            if (formIsValid == false) {
                Log.d("myLogs", "Данные не корректны")
                return@launch
            }

            //переход на главную страницу
            val user: UserEntity? = localUserRepository.getUserByFirstName(firstName)
            user?.let {
                if (password.equals(it.password)){
                    Log.d("myLogs", "Вход выполнен успешно")
                    _action.postValue(Action.NavigateToHomePage)
                }else{
                    Log.d("myLogs", "Неверный ввод пароля")
                }
                return@launch
            }

            Log.d("myLogs", "//Данной учетной записи не существует")
        }
    }

    fun logInJob() {
        val jobLogIn = GlobalScope.launch(Dispatchers.IO) {
            login()
        }.start()
    }
}