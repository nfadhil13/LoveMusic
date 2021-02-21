package com.fdev.lovemusic.datasource.network

object NetworkErrorConst {

    const val UNKNOWN_ERROR = "Terjadi kesalahan pada server yang tidak diketahui"
    const val TIMEOUT_ERROR = "Gagal mendapatkan data , coba cek internet anda"

    object USER{
        const val USERNAME_EXIST = "username sudah ada yang menggunakan"
    }

}