package com.rosseti.data.model

import com.squareup.moshi.Json

data class PlayerModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String
)

data class GameModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "gamer_id")
    val playerId: String,
    @Json(name = "adversary_id")
    val adversaryId: String,
    @Json(name = "adversary")
    val adversary: String,
    @Json(name = "player_name")
    val playerName: String,
    @Json(name = "score")
    val score: String,
    @Json(name = "score_adversary")
    val scoreAdversary: String
)

