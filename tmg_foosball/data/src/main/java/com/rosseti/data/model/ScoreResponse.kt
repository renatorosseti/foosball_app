package com.rosseti.data.model

import com.rosseti.domain.entity.ResultEntity
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
    val scores: String,
    @Json(name = "results")
    val results: List<ResultModel>
)

data class ResultModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "adversary")
    val adversary: String,
    @Json(name = "score")
    val score: String,
    @Json(name = "adversary_score")
    val adversaryScore: String
)

fun ScoreModel.mapToDomain(): ScoreEntity =
    ScoreEntity(
        id = id,
        name = name,
        matches = matches,
        scores = scores,
        results = results.map {
            ResultEntity(
                id = it.id,
                adversary = it.adversary,
                score = it.score,
                adversaryScore = it.adversaryScore,
                result = "${it.score} x ${it.adversaryScore}",
                isWinner = it.score > it.adversaryScore
            )
        })


