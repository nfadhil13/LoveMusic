package com.fdev.lovemusic.datasource.network.framework.model

data class UserDto(
    val id : String,
    val username : String,
    val email : String = "",
    val url_photo : String = ""
)