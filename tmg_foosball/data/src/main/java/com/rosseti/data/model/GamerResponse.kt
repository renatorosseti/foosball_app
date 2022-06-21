package com.rosseti.data.model

import com.rosseti.domain.entity.PlayerEntity
import com.squareup.moshi.Json

class GamerResponse : ArrayList<GamerModel>()

data class GamerModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String
)

data class GameModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "gamer_id")
    val gamerId: String,
    @Json(name = "adversary")
    val adversary: String,
    @Json(name = "score")
    val score: Int,
    @Json(name = "score_adversary")
    val scoreAdversary: Int
)

fun GamerModel.mapToDomain(): PlayerEntity =
    PlayerEntity(
        id = id,
        name = name
    )


