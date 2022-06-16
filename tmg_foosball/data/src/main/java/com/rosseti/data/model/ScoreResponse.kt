package com.rosseti.data.model

import com.rosseti.domain.entity.ScoreEntity
import com.squareup.moshi.Json

class ScoreResponse : ArrayList<ScoreModel>()

data class ScoreModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "matches")
    val matches: String,
    @Json(name = "scores")
    val scores: String
)

fun ScoreModel.mapToDomain(): ScoreEntity =
    ScoreEntity(id = id, name = name, matches = matches, scores = scores)
