package com.fdev.lovemusic.datasource.network.business.abstraction

import com.fdev.lovemusic.datasource.DataSourceResource
import com.fdev.lovemusic.model.User

interface UserNetworkDatasource {

    suspend fun checkIfUserExist(idToken : String) : DataSourceResource<Boolean>

    suspend fun signUp(user : User , idToken : String) : DataSourceResource<User>

}