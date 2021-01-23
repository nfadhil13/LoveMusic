package com.fdev.lovemusic.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fdev.lovemusic.repository.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _loading : MutableLiveData<Boolean> = MutableLiveData(false)

    val loading : LiveData<Boolean>
        get() = _loading


    private val _errorMessage : MutableLiveData<String> = MutableLiveData("")

    val errorMessage : LiveData<String>
        get() = _errorMessage

    protected  fun <T> onCollect(response : Resource<T>, executeOnSuccess : (Resource.Success<T>) -> Unit) {
        when(response){
            is Resource.Success -> {
                setLoading(false)
                executeOnSuccess(response)
            }
            is Resource.Error -> {
                setLoading(false)
                response.message?.let { message ->
                    setErrorMessage(
                            message = message
                    )
                }

            }
            is Resource.Loading -> {
                setLoading(true)
            }
        }
    }



    private fun setLoading(isLoading : Boolean) {
        viewModelScope.launch(Dispatchers.Main){
            _loading.value = isLoading
        }
    }

    private fun setErrorMessage(message : String) {
        viewModelScope.launch(Dispatchers.Main){
            _errorMessage.value = message
        }
    }
}