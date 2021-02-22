package com.fdev.lovemusic.presentation.auth.register

import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.util.SingleEvent

data class RegisterViewState(
        var registerResult: SingleEvent<RegisterResult?> = SingleEvent(null)
){

    data class RegisterResult(val user : User)

}