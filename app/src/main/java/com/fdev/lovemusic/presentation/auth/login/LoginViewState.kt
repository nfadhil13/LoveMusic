package com.fdev.lovemusic.presentation.auth.login

import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.util.SingleEvent


data class LoginViewState(
        var loginOrNullResult: SingleEvent<LoginOrNullResult?> = SingleEvent(null)
){

    data class LoginOrNullResult(val user : User?)

}