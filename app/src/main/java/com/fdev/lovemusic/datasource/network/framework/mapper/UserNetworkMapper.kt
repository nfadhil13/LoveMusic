package com.fdev.lovemusic.datasource.network.framework.mapper

import com.fdev.lovemusic.datasource.DomainMapper
import com.fdev.lovemusic.datasource.network.framework.model.UserDto
import com.fdev.lovemusic.model.User
import javax.inject.Inject

class UserNetworkMapper
@Inject
constructor() : DomainMapper<UserDto , User> {


    override fun mapFromDomain(domain: User): UserDto {
        return UserDto(
                id = domain.id,
                email = domain.email,
                url_photo = domain.url_photo,
                username = domain.username
        )
    }

    override fun mapToDomain(t: UserDto): User {
        return User(
                id = t.id,
                email = t.email,
                url_photo = t.url_photo,
                username = t.username
        )
    }


}