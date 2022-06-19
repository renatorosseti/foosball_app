package com.rosseti.data.model

import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.GamerEntity
import com.squareup.moshi.Json

class ScoreResponse : ArrayList<GamerModel>()

data class GamerModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "games")
    val games: MutableList<GameModel>
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
        name = name,
        matches = games.size.toString(),
        scores = games.filter { it.score > it.scoreAdversary }.size.toString(),
        games = games.map {
            GameEntity(
                id = it.id,
                adversary = it.adversary,
                score = it.score,
                scoreAdversary = it.scoreAdversary,
                result = "${it.score} x ${it.scoreAdversary}",
                isWinner = it.score > it.scoreAdversary
            )
        }.toMutableList()
    )


