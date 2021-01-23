package com.fdev.lovemusic.datasource

interface DomainMapper<T , Domain>{

    fun mapFromDomain(domain : Domain) : T

    fun mapToDomain(t : T) : Domain


}