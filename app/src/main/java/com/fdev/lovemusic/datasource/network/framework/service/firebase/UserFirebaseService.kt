package com.fdev.lovemusic.datasource.network.framework.service.firebase

import com.fdev.lovemusic.datasource.network.NetworkResource
import com.fdev.lovemusic.datasource.network.NetworkSaveCall
import com.fdev.lovemusic.datasource.network.framework.service.firebase.mapper.UserFirebaseMapper
import com.fdev.lovemusic.datasource.network.framework.service.firebase.model.UserFirebaseDto
import com.fdev.lovemusic.model.User
import com.fdev.lovemusic.util.UserInteraction
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


    suspend fun checkIfUserExist(idToken: String): NetworkResource<Boolean> = object  : NetworkSaveCall<Boolean>(){
        override suspend fun networkCall(): NetworkResource<Boolean> {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            authResult.user?.let {
                val isExist = !firestore
                        .collection(FirestoreDocuments.UserDocument.documentName)
                        .whereEqualTo(FirestoreDocuments.UserDocument.id, it.uid)
                        .get()
                        .await()
                        .isEmpty
                return NetworkResource.Success(
                        data = isExist,
                        onSuccessInteraction = UserInteraction.DoNothing
                )
            } ?: return NetworkResource.Error(onErrorInteraction = UserInteraction.ShowToast("Gagal mendapatkan user silahkan coba lagi" ))
        }

    }.fetch()


    suspend fun signUp(user: User, idToken: String) : NetworkResource<User> = object : NetworkSaveCall<User>(){
        override suspend fun networkCall(): NetworkResource<User> {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = firebaseAuth.signInWithCredential(credential).await()
                authResult.user?.let {
                    firestore
                            .collection(FirestoreDocuments.UserDocument.documentName)
                            .document(it.uid)
                            .set(userFirebaseMapper.mapFromDomain(user))
                            .await()
                    return NetworkResource.Success(
                            data = user,
                            onSuccessInteraction = UserInteraction.DoNothing
                    )
                } ?: return NetworkResource.Error(
                        onErrorInteraction = UserInteraction.ShowToast("Gagal mendapatkan user silahkan coba lagi" )
                )
        }

    }.fetch()

}