package com.fdev.lovemusic.repository.user

import com.fdev.lovemusic.datasource.network.business.abstraction.UserNetworkDatasource
import com.fdev.lovemusic.repository.Resource
import com.fdev.lovemusic.repository.saveCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIfUserExist
@Inject
constructor(
        private val userNetworkDatasource: UserNetworkDatasource
){

    fun fetch(
            idToken : String,
            dispatcher: CoroutineDispatcher
    ): Flow<Resource<Boolean>> = saveCall(
            tobeExecute = {
                userNetworkDatasource.checkIfUserExist(idToken)
            },
            dispatcher = dispatcher

    )

}