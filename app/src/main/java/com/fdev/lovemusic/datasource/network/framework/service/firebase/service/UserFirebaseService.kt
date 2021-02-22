package com.fdev.lovemusic.datasource.network.framework.service.firebase.service

import com.fdev.lovemusic.datasource.DataSourceResource
import com.fdev.lovemusic.datasource.network.NetworkErrorConst
import com.fdev.lovemusic.datasource.network.NetworkSaveCall
import com.fdev.lovemusic.datasource.network.framework.service.firebase.FirestoreDocuments
import com.fdev.lovemusic.datasource.network.framework.service.firebase.mapper.UserFirebaseMapper
import com.fdev.lovemusic.datasource.network.framework.service.firebase.model.UserFirebaseDto
import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.util.UIInteraction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class  UserFirebaseService
@Inject
constructor(
        private val firebaseAuth: FirebaseAuth,
        private val firestore: FirebaseFirestore,
        private val userFirebaseMapper: UserFirebaseMapper
) {



    suspend fun signInOrNull(idToken: String): DataSourceResource<User?> = object  : NetworkSaveCall<User?>(){
        override suspend fun networkCall(): DataSourceResource<User?> {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val user = authResult.user ?: return DataSourceResource.Error(onErrorInteraction = UIInteraction.ShowToast("Gagal mendapatkan user silahkan coba lagi" ))
            val userDocument = firestore
                    .collection(FirestoreDocuments.UserDocument.documentName)
                    .document(user.uid)
                    .get()
                    .await()

            val userDto = userDocument.toObject(UserFirebaseDto::class.java) ?: return DataSourceResource.Success(
                    data = null,
                    onSuccessInteraction = UIInteraction.DoNothing
            )


            return DataSourceResource.Success(
                    data = userFirebaseMapper.mapToDomain(userDto),
                    onSuccessInteraction = UIInteraction.DoNothing
            )

        }

    }.fetch()


    suspend fun signUp(user: User, idToken: String) : DataSourceResource<User> = object : NetworkSaveCall<User>(){

        override suspend fun networkCall(): DataSourceResource<User> {

            var userUid : String? = firebaseAuth.currentUser?.uid
            if(userUid == null){
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = firebaseAuth.signInWithCredential(credential).await()
                userUid = authResult.user?.uid
            }
            if(userUid == null) return DataSourceResource.Error(
                    onErrorInteraction = UIInteraction.ShowToast("Gagal mendapatkan user silahkan coba lagi" )
            )

            val isUsernameAvailable =
                    !firestore.collection(FirestoreDocuments.UserDocument.documentName)
                            .whereEqualTo(FirestoreDocuments.UserDocument.username, user.username)
                            .get()
                            .await()
                            .isEmpty

            if(isUsernameAvailable){
                return DataSourceResource.Error(
                        onErrorInteraction = UIInteraction.GenericMessage(NetworkErrorConst.USER.USERNAME_EXIST)
                )
            }
            val newUser = User(
                    email = firebaseAuth.currentUser?.email ?: "",
                    id = userUid,
                    username = user.username
            )
            firestore.collection(FirestoreDocuments.UserDocument.documentName)
                    .document(userUid)
                    .set(newUser)
            return DataSourceResource.Success(
                    data = user,
                    onSuccessInteraction = UIInteraction.DoNothing
            )


        }

    }.fetch()

    suspend fun checkLastLoggedInUser(): DataSourceResource<User?> = object  : NetworkSaveCall<User?>(){
        override suspend fun networkCall(): DataSourceResource<User?> {

            val onUserNullResponse =  DataSourceResource.Success<User?>(
                    data = null,
                    onSuccessInteraction = UIInteraction.DoNothing
            )

            val userUID = firebaseAuth.currentUser?.uid ?: return onUserNullResponse
            val userDocument = firestore
                    .collection(FirestoreDocuments.UserDocument.documentName)
                    .document(userUID)
                    .get()
                    .await()

            val userDto = userDocument.toObject(UserFirebaseDto::class.java) ?: return onUserNullResponse

            return DataSourceResource.Success(
                    data = userFirebaseMapper.mapToDomain(userDto),
                    onSuccessInteraction = UIInteraction.DoNothing
            )

        }

    }.fetch()

}