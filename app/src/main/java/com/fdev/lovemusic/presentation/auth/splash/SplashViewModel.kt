package com.fdev.lovemusic.presentation.auth.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fdev.lovemusic.interactors.user.UserInteractor
import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.presentation.BaseViewModel
import com.fdev.lovemusic.util.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
constructor(
       private val userInteractor: UserInteractor
) : BaseViewModel(){


    private val _splashViewState : MutableLiveData<SplashViewState> = MutableLiveData(SplashViewState())



    val splashViewState : LiveData<SplashViewState>
        get() = _splashViewState

    fun checkLastLoggedInUser(){
        viewModelScope.launch {
            userInteractor.lastLoggedIn.fetch(IO).collect {
                onCollect(
                        response =  it,
                        executeOnSuccess = { resource ->
                            setCheckLastLoggedInResult(resource.data)
                        }
                )
            }
        }
    }

    private fun setCheckLastLoggedInResult(data: User?) {
        viewModelScope.launch(Dispatchers.Main){
            val currentValue = _splashViewState.value
            print("value lamna : ${currentValue?.checkLastLoggedInResult?.peek()}")
            currentValue?.checkLastLoggedInResult = SingleEvent(SplashViewState.CheckLastLoggedInResult(data))
            println("ada value baruuuu : ${currentValue?.checkLastLoggedInResult?.peek()}")
            _splashViewState.value = currentValue
        }
    }

}