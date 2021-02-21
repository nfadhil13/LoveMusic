package com.fdev.lovemusic.datasource.network.business.implementation

import com.fdev.lovemusic.datasource.DataSourceResource
import com.fdev.lovemusic.datasource.network.business.abstraction.UserNetworkDatasource
import com.fdev.lovemusic.datasource.network.framework.service.firebase.UserFirebaseService
import com.fdev.lovemusic.model.User
import javax.inject.Inject

class UserNetworkDatasourceImpl
@Inject
constructor(
        private val userFirebaseService: UserFirebaseService,
) : UserNetworkDatasource {


    override suspend fun checkIfUserExist(idToken: String): DataSourceResource<Boolean> {
        return userFirebaseService.checkIfUserExist(idToken = idToken)
    }

    override suspend fun signUp(user: User , idToken: String): DataSourceResource<User> {
        return userFirebaseService.signUp(
                        user = user,
                        idToken = idToken
                )

    }

}