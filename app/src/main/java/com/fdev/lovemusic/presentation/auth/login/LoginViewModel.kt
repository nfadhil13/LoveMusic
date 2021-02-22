package com.fdev.lovemusic.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fdev.lovemusic.presentation.BaseViewModel
import com.fdev.lovemusic.interactors.user.UserInteractor
import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.presentation.auth.login.LoginViewState.*
import com.fdev.lovemusic.util.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
        private val userInteractor: UserInteractor
) : BaseViewModel(){

    private val _loginStateView : MutableLiveData<LoginViewState> = MutableLiveData(LoginViewState())



    val loginStateView : LiveData<LoginViewState>
        get() = _loginStateView

    var currentIdToken : String = ""
       private set


    fun loginOrNull(idTokenString: String){
        currentIdToken = idTokenString
        viewModelScope.launch(IO){
            userInteractor.checkIfUserExist.fetch(idTokenString , IO).collect { response ->
                onCollect(response , executeOnSuccess =  { successResponse ->
                        println("Success hasil data : ${successResponse.data}")
                        setLoginOrNullResult(successResponse.data)
                })
            }
        }
    }

    private fun setLoginOrNullResult(user : User?) {
        viewModelScope.launch(Dispatchers.Main){
            val currentValue = _loginStateView.value
            currentValue?.loginOrNullResult = SingleEvent(LoginOrNullResult(user))
            _loginStateView.value = currentValue
        }
    }




}