package com.rosseti.data.model

import com.rosseti.domain.entity.GamerEntity
import com.squareup.moshi.Json

class GamerResponse : ArrayList<GamerModel>()

data class GamerModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
)

data class GameModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "adversary")
    val adversary: String,
    @Json(name = "score")
    val score: String,
    @Json(name = "score_adversary")
    val scoreAdversary: String
)

fun GamerModel.mapToDomain(): GamerEntity =
    GamerEntity(
        id = id,
        name = name
    )


