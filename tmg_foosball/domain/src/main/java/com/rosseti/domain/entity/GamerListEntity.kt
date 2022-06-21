package com.rosseti.domain.entity

data class GamerListEntity(
    var page: Int? = null,
    var totalResults: Int? = null,
    var totalPages: Int = 1,
    val playerList: List<PlayerEntity> = emptyList()
)

data class GameListEntity(
    var page: Int? = null,
    var totalResults: Int? = null,
    var totalPages: Int = 1,
    val gameList: List<GameEntity> = emptyList()
)
