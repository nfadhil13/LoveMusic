package com.fdev.lovemusic.di

import com.fdev.lovemusic.datasource.network.business.abstraction.UserNetworkDatasource
import com.fdev.lovemusic.datasource.network.business.implementation.UserNetworkDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(FragmentComponent::class)
abstract class ViewModelBind {


    @Binds
    abstract fun bindUserNetworkSource(userNetworkDatasourceImpl: UserNetworkDatasourceImpl) : UserNetworkDatasource
}