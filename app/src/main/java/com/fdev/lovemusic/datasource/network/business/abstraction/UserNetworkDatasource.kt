package com.fdev.lovemusic.datasource.network.business.abstraction

import com.fdev.lovemusic.datasource.network.NetworkResource
import com.fdev.lovemusic.model.User

interface UserNetworkDatasource {

    suspend fun checkIfUserExist(idToken : String) : NetworkResource<Boolean>

    suspend fun signUp(user : User , idToken : String) : NetworkResource<User>

}