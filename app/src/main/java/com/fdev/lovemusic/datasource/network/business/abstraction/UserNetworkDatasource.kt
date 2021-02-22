package com.fdev.lovemusic.datasource.network.business.abstraction

import com.fdev.lovemusic.datasource.DataSourceResource
import com.fdev.lovemusic.model.User

interface UserNetworkDatasource {

    suspend fun signInOrNull(idToken : String) : DataSourceResource<User?>

    suspend fun signUp(user : User , idToken : String) : DataSourceResource<User>

    suspend fun checkLastLoggedInUser() : DataSourceResource<User?>
}