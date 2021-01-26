package com.fdev.lovemusic.repository.user


import javax.inject.Inject


class UserInteractor
@Inject
constructor(
       val checkIfUserExist: CheckIfUserExist
)