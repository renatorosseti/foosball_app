package com.rosseti.domain.entity

data class GamerListEntity(
    var page: Int? = null,
    var totalResults: Int? = null,
    var totalPages: Int = 1,
    val gamerList: List<GamerEntity> = emptyList()
)

data class GameListEntity(
    var page: Int? = null,
    var totalResults: Int? = null,
    var totalPages: Int = 1,
    val gameList: List<GameEntity> = emptyList()
)
