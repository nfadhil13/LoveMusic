package com.fdev.lovemusic.datasource.network.business.implementation

import com.fdev.lovemusic.datasource.network.business.abstraction.UserNetworkDatasource
import com.fdev.lovemusic.datasource.network.framework.mapper.UserNetworkMapper
import com.fdev.lovemusic.datasource.network.framework.service.UserFirebaseService
import com.fdev.lovemusic.model.User
import javax.inject.Inject

class UserNetworkDatasourceImpl
@Inject
constructor(
      private val userFirebaseService: UserFirebaseService,
      private val userNetworkMapper: UserNetworkMapper
) : UserNetworkDatasource {


    override suspend fun checkIfUserExist(idToken: String): Boolean {
        return userFirebaseService.checkIfUserExist(idToken = idToken)
    }

    override suspend fun signUp(user: User , idToken: String): User {
        return userNetworkMapper.mapToDomain(
                userFirebaseService.signUp(
                        userDto =  userNetworkMapper.mapFromDomain(user),
                        idToken = idToken
                )
        )
    }

}