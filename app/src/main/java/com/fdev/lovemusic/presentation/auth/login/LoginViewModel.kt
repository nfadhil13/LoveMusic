package com.fdev.lovemusic.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fdev.lovemusic.presentation.BaseViewModel
import com.fdev.lovemusic.repository.user.UserInteractor
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

    private val _isUserExist : MutableLiveData<Boolean?> = MutableLiveData(null)

    val isUserExist : LiveData<Boolean?>
        get() = _isUserExist

    fun checkIfUserExist(idTokenString: String){
        viewModelScope.launch(IO){
            userInteractor.checkIfUserExist.fetch(idTokenString , IO).collect { response ->
                onCollect(response , executeOnSuccess =  { successResponse ->
                    successResponse.data?.let{
                        setIsUserExist(successResponse.data)
                    }

                })
            }
        }
    }

    private fun setIsUserExist(userExist : Boolean) {
        viewModelScope.launch(Dispatchers.Main){
            _isUserExist.value = userExist
        }
    }



}