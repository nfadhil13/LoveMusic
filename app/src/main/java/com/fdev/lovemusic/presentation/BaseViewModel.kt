package com.fdev.lovemusic.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fdev.lovemusic.interactors.Resource
import com.fdev.lovemusic.util.SingleEvent
import com.fdev.lovemusic.util.UIInteraction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _loading : MutableLiveData<Boolean> = MutableLiveData(false)

    val loading : LiveData<Boolean>
        get() = _loading


    protected val _userInteraction : MutableLiveData<SingleEvent<UIInteraction>> = MutableLiveData(SingleEvent(UIInteraction.DoNothing))

    val userInteraction : LiveData<SingleEvent<UIInteraction>>
        get() = _userInteraction

    protected  fun <T> onCollect(response : Resource<T>, executeOnSuccess : (Resource.Success<T>) -> Unit) {
        when(response){
            is Resource.Success -> {
                setLoading(false)
                setUserInteraction(response.onSuccessInteraction)
                executeOnSuccess(response)
            }
            is Resource.Error -> {
                setLoading(false)
                setUserInteraction(userInteraction = response.onErrorInteraction)

            }
            is Resource.Loading -> {
                setLoading(true)
            }
        }
    }



    protected fun setLoading(isLoading : Boolean) {
        viewModelScope.launch(Dispatchers.Main){
            _loading.value = isLoading
        }
    }

    protected fun setUserInteraction(userInteraction: UIInteraction) {
        viewModelScope.launch(Dispatchers.Main){
            _userInteraction.value = SingleEvent(userInteraction)
        }
    }
}