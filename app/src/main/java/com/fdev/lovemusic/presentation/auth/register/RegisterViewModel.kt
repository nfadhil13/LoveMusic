package com.fdev.lovemusic.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fdev.lovemusic.interactors.user.UserInteractor
import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.presentation.BaseViewModel
import com.fdev.lovemusic.presentation.auth.login.LoginViewState
import com.fdev.lovemusic.util.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
        private val userInteractor: UserInteractor
): BaseViewModel() {


    private val _registerViewState = MutableLiveData(RegisterViewState())

    private var currentIdToken : String = ""

    val registerViewState
        get() = _registerViewState as LiveData<RegisterViewState>


    fun register(username : String){
        viewModelScope.launch {
            val newUser = User(username = username)
            userInteractor.signUp.googleSignUp(
                    idToken = currentIdToken,
                    user = newUser,
                    dispatcher = IO
            ).collect {
                onCollect(
                        response = it,
                        executeOnSuccess =  { result ->
                            setRegisterResult(result.data)
                        }
                )
            }
        }

    }


    private fun setRegisterResult(user : User) {
        viewModelScope.launch(Dispatchers.Main){
            val currentValue = _registerViewState.value
            currentValue?.registerResult = SingleEvent(RegisterViewState.RegisterResult(user))
            _registerViewState.value = currentValue
        }
    }

    fun setCurrentIdToken(idToken : String){
        currentIdToken = idToken
    }

}