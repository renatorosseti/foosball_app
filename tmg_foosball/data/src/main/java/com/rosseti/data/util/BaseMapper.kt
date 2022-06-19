package com.rosseti.data.util

import com.rosseti.data.model.GamerModel
import com.rosseti.data.model.mapToDomain
import com.rosseti.domain.entity.GamerEntity

abstract class BaseMapper<T, K> {

    abstract fun transformFrom(source: K): T

    abstract fun transformTo(source: T): K

    fun transformFromList(source: List<K>?): List<T> {
        return source?.map { src -> transformFrom(src) } ?: emptyList()
    }

    fun transformToList(source: List<T>): List<K> {
        return source.map { src -> transformTo(src) }
    }

    fun List<GamerModel>.mapLisToDomain(): List<GamerEntity> = map { it.mapToDomain() }

}