package com.fdev.lovemusic.di

import com.fdev.lovemusic.datasource.network.business.abstraction.UserNetworkDatasource
import com.fdev.lovemusic.datasource.network.business.implementation.UserNetworkDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelBind {


    @Binds
    @ViewModelScoped
    abstract fun bindUserNetworkSource(userNetworkDatasourceImpl: UserNetworkDatasourceImpl) : UserNetworkDatasource
}