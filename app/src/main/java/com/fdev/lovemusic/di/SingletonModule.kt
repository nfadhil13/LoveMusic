package com.fdev.lovemusic.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {


    @Provides
    fun provideFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()


    @Provides
    fun provideFirebaseFirestore() : FirebaseFirestore = FirebaseFirestore.getInstance()

}