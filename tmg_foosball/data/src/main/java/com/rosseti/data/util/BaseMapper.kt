package com.rosseti.data.util

import com.rosseti.data.model.ScoreModel
import com.rosseti.data.model.mapToDomain
import com.rosseti.domain.entity.ScoreEntity


abstract class BaseMapper<T, K> {

    abstract fun transformFrom(source: K): T

    abstract fun transformTo(source: T): K

    fun transformFromList(source: List<K>?): List<T> {
        return source?.map { src -> transformFrom(src) } ?: emptyList()
    }

    fun transformToList(source: List<T>): List<K> {
        return source.map { src -> transformTo(src) }
    }

    fun List<ScoreModel>.mapLisToDomain(): List<ScoreEntity> = map { it.mapToDomain() }

    fun toList(source: List<T>): List<K> = source.map { src -> transformTo(src) }

}