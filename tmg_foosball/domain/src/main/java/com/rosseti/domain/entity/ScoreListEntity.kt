package com.rosseti.domain.entity

data class ScoreListEntity(
    var page: Int? = null,
    var totalResults: Int? = null,
    var totalPages: Int = 1,
    val scoreList: List<ScoreEntity> = emptyList()
)

