package com.fdev.lovemusic.datasource.network.framework.service.firebase

import com.fdev.lovemusic.datasource.DataSourceResource
import com.fdev.lovemusic.datasource.network.NetworkErrorConst
import com.fdev.lovemusic.datasource.network.NetworkSaveCall
import com.fdev.lovemusic.datasource.network.framework.service.firebase.mapper.UserFirebaseMapper
import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.util.UIInteraction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class  UserFirebaseService
@Inject
constructor(
        private val firebaseAuth: FirebaseAuth,
        private val firestore: FirebaseFirestore,
        private val userFirebaseMapper: UserFirebaseMapper
) {



    suspend fun checkIfUserExist(idToken: String): DataSourceResource<Boolean> = object  : NetworkSaveCall<Boolean>(){
        override suspend fun networkCall(): DataSourceResource<Boolean> {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val user = authResult.user ?: return DataSourceResource.Error(onErrorInteraction = UIInteraction.ShowToast("Gagal mendapatkan user silahkan coba lagi" ))
            val isExist = firestore
                    .collection(FirestoreDocuments.UserDocument.documentName)
                    .document(user.uid)
                    .get()
                    .await()
                    .exists()
            return DataSourceResource.Success(
                    data = isExist,
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
                userUid = authResult.user?.uid ?: return DataSourceResource.Error(
                        onErrorInteraction = UIInteraction.ShowToast("Gagal mendapatkan user silahkan coba lagi" )
                )
            }
            val isUsernameAvailable =
                    firestore.collection(FirestoreDocuments.UserDocument.documentName)
                            .whereEqualTo(FirestoreDocuments.UserDocument.username , user.username)
                            .get()
                            .await()
                            .isEmpty

            if(isUsernameAvailable){
                return DataSourceResource.Error(
                        onErrorInteraction = UIInteraction.GenericMessage(NetworkErrorConst.USER.USERNAME_EXIST)
                )
            }
            user.email = firebaseAuth.currentUser?.email ?: ""
            firestore.collection(FirestoreDocuments.UserDocument.documentName)
                    .document(userUid)
                    .set(user)
            return DataSourceResource.Success(
                    data = user,
                    onSuccessInteraction = UIInteraction.DoNothing
            )


        }

    }.fetch()

}