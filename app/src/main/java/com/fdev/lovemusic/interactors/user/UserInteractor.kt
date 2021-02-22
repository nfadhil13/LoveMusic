package com.fdev.lovemusic.interactors.user


import javax.inject.Inject


class UserInteractor
@Inject
constructor(
        val checkIfUserExist: LoginOrNull,
        val signUp: UserSignUp,
        val lastLoggedIn: CheckLastLoggedIn
)