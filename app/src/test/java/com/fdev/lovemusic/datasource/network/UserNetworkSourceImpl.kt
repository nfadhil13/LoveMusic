//package com.fdev.lovemusic.datasource.network
//
//import com.fdev.lovemusic.data.UserDummiesData
//import com.fdev.lovemusic.datasource.DataSourceResource
//import com.fdev.lovemusic.datasource.network.business.abstraction.UserNetworkDatasource
//import com.fdev.lovemusic.model.User
//
//class UserNetworkSourceImpl(
//    val dummiesData: UserDummiesData
//):UserNetworkDatasource {
//
//
//    override suspend fun checkIfUserExist(idToken: String): DataSourceResource<Boolean> {
//        dummiesData.data.forEach {
//            if(idToken === it.id){
//                return DataSourceResource.Success(
//                        data = true,
//                )
//            }
//        }
//        return DataSourceResource.Success(
//                data = false
//        )
//    }
//
//    override suspend fun signUp(user: User, idToken: String): DataSourceResource<User> {
//        dummiesData.data.forEach {
//            if(idToken === it.id){
//                return D
//            }
//        }
//    }
//}