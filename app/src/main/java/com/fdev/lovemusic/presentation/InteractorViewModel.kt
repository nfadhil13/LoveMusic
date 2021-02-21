package com.fdev.lovemusic.presentation

import com.fdev.lovemusic.util.UIInteraction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InteractorViewModel
@Inject constructor(

) : BaseViewModel() {


    fun showUserInteraction(userInteraction: UIInteraction) {
        setUserInteraction(userInteraction)
    }


    fun startLoading() {
        setLoading(true)
    }

    fun finishLoading() {
        setLoading(false)
    }

}