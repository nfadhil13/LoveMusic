package com.fdev.lovemusic.datasource.network.framework.service.firebase.mapper

import com.fdev.lovemusic.datasource.DomainMapper
import com.fdev.lovemusic.datasource.network.framework.service.firebase.model.UserFirebaseDto
import com.fdev.lovemusic.model.User
import javax.inject.Inject

class UserFirebaseMapper
@Inject
constructor() : DomainMapper<UserFirebaseDto , User> {


    override fun mapFromDomain(domain: User): UserFirebaseDto {
        return UserFirebaseDto(
                id = domain.id,
                email = domain.email,
                url_photo = domain.url_photo,
                username = domain.username
        )
    }

    override fun mapToDomain(t: UserFirebaseDto): User {
        return User(
                id = t.id,
                email = t.email,
                url_photo = t.url_photo,
                username = t.username
        )
    }


}