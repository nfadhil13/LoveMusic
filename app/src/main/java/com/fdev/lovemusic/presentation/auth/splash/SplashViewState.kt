package com.fdev.lovemusic.presentation.auth.splash

import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.util.SingleEvent

data class SplashViewState(
        var checkLastLoggedInResult : SingleEvent<CheckLastLoggedInResult?> =  SingleEvent(null)
){
    data class CheckLastLoggedInResult(
            val user : User?
    )
}