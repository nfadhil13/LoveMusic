package com.fdev.lovemusic.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fdev.lovemusic.interactors.user.UserInteractor
import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
        private val userInteractor: UserInteractor
): BaseViewModel() {


    private val _registerTask = MutableLiveData<Boolean?>(null)

    val registerTask
        get() = _registerTask as LiveData<Boolean?>


    private fun register(username : String , idToken : String){
        viewModelScope.launch {
            val newUser = User(username = username)
            userInteractor.signUp.googleSignUp(
                    idToken = idToken,
                    user = newUser,
                    dispatcher = IO
            ).collect {
                onCollect(
                        response = it,
                        executeOnSuccess =  {

                        }
                )
            }
        }

    }

}