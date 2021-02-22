package com.fdev.lovemusic.presentation.auth

import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.presentation.ActivityBaseViewModel
import com.fdev.lovemusic.presentation.BaseViewModel
import com.fdev.lovemusic.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthActivityViewModel
@Inject constructor(
    _sessionManager : SessionManager
) : ActivityBaseViewModel(_sessionManager) {



    fun setCurrentUser(user : User){
        sessionManager.login(user)
    }


}