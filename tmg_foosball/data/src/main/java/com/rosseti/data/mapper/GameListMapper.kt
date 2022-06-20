package com.rosseti.data.mapper

import com.rosseti.data.model.GameModel
import com.rosseti.data.util.BaseMapper
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.GameListEntity

object GameListToDomainMapper : BaseMapper<GameListEntity, List<GameModel>>() {

    override fun transformFrom(source: List<GameModel>): GameListEntity {

        return GameListEntity(gameList = GameModelToDomainMapper.transformToList(source))
    }

    override fun transformTo(source: GameListEntity): List<GameModel> {
        return source.gameList.map {
            GameModel(
                id = it.id,
                adversary = it.adversary,
                score = it.score,
                scoreAdversary = it.scoreAdversary
            )
        }
    }
}

object GameModelToDomainMapper : BaseMapper<GameModel, GameEntity>() {

    override fun transformFrom(source: GameEntity): GameModel = GameModel(
        id = source.id,
        adversary = source.adversary,
        score = source.score,
        scoreAdversary = source.scoreAdversary
    )

    override fun transformTo(source: GameModel): GameEntity =
        createScoreEntity(source)

    private fun createScoreEntity(it: GameModel) = GameEntity(
        id = it.id,
        adversary = it.adversary,
        score = it.score,
        scoreAdversary = it.scoreAdversary,
        result = "${it.score} x ${it.scoreAdversary}",
        isWinner = it.score > it.scoreAdversary
    )
}