package com.fdev.lovemusic.datasource.network.framework.service.firebase.model

data class UserFirebaseDto(
    val id : String,
    val username : String,
    val email : String = "",
    val url_photo : String = ""
)