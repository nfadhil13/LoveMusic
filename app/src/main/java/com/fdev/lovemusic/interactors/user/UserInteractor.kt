package com.fdev.lovemusic.interactors.user


import javax.inject.Inject


class UserInteractor
@Inject
constructor(
       val checkIfUserExist: CheckIfUserExist,
       val signUp: UserSignUp
)