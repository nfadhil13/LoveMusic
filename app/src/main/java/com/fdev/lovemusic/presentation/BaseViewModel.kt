package com.fdev.lovemusic.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fdev.lovemusic.repository.Resource
import com.fdev.lovemusic.util.SingleEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _loading : MutableLiveData<Boolean> = MutableLiveData(false)

    val loading : LiveData<Boolean>
        get() = _loading


    protected val _errorMessage : MutableLiveData<SingleEvent<String>> = MutableLiveData(SingleEvent(""))

    val errorMessage : LiveData<SingleEvent<String>>
        get() = _errorMessage

    protected  fun <T> onCollect(response : Resource<T>, executeOnSuccess : (Resource.Success<T>) -> Unit) {
        when(response){
            is Resource.Success -> {
                setLoading(false)
                executeOnSuccess(response)
            }
            is Resource.Error -> {
                setLoading(false)
                _errorMessage.value = SingleEvent(response.onErrorInteraction.message)

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
            _errorMessage.value = SingleEvent(message)
        }
    }
}