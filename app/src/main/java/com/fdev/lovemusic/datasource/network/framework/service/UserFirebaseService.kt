package com.fdev.lovemusic.datasource.network.framework.service

import com.fdev.lovemusic.datasource.network.framework.model.UserDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserFirebaseService
@Inject
constructor(
        private val firebaseAuth: FirebaseAuth,
        private val firestore: FirebaseFirestore
) {

    suspend fun checkIfUserExist(idToken: String): Boolean {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        try {
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            authResult.user?.let {
                return !firestore
                    .collection(FirestoreDocuments.UserDocument.documentName)
                    .whereEqualTo(FirestoreDocuments.UserDocument.id, it.uid)
                    .get()
                    .await()
                    .isEmpty
            } ?: throw Exception("Failed To Sign Up or Login")

        } catch (exception: Exception) {
            throw exception
        }
    }

    suspend fun signUp(userDto: UserDto , idToken: String) : UserDto {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        try {
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            authResult.user?.let {
                val result =  firestore
                    .collection(FirestoreDocuments.UserDocument.documentName)
                    .document(it.uid)
                    .set(userDto)
                    .await()
                return userDto
            } ?: throw Exception("Failed To Sign Up or Login")
        } catch (exception: Exception) {
            throw exception
        }
    }
}