package com.rosseti.data.mapper

import androidx.lifecycle.Transformations.map
import com.rosseti.data.model.GameModel
import com.rosseti.data.model.GamerModel
import com.rosseti.data.model.ScoreResponse
import com.rosseti.data.util.BaseMapper
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.GamerEntity
import com.rosseti.domain.entity.GamerListEntity

object ScoreListToDomainMapper : BaseMapper<GamerListEntity, List<GamerModel>>() {

    override fun transformFrom(source: List<GamerModel>): GamerListEntity {

        return GamerListEntity(gamerList = ScoreModelToDomainMapper.transformToList(source))
    }

    override fun transformTo(source: GamerListEntity): ScoreResponse {
        val scoreResponse = ScoreResponse()
        scoreResponse.addAll(source.gamerList.let {
            ScoreModelToDomainMapper.transformFromList(
                it
            )
        })
        return scoreResponse
    }
}

object ScoreModelToDomainMapper : BaseMapper<GamerModel, GamerEntity>() {

    override fun transformFrom(source: GamerEntity): GamerModel = GamerModel(
        id = source.id,
        name = source.name,
        games = source.games.map { GameModel(it.id, it.adversary, it.score, it.scoreAdversary) }.toMutableList()
    )

    override fun transformTo(source: GamerModel): GamerEntity =
        createScoreEntity(source)

    private fun createScoreEntity(it: GamerModel) = GamerEntity(
        id = it.id,
        name = it.name,
        matches = it.games.size.toString(),
        scores = it.games.filter { it.score > it.scoreAdversary }.size.toString(),
        games = it.games.map { res ->
            GameEntity(
                id = res.id,
                adversary = res.adversary,
                score = res.score,
                scoreAdversary = res.scoreAdversary,
                result = "${res.score} x ${res.scoreAdversary}",
                isWinner = res.score > res.scoreAdversary
            )
        }.toMutableList()
    )
}