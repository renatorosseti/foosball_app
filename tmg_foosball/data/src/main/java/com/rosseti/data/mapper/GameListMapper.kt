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
                gamerId = it.gamerId,
                adversary = it.adversary,
                score = it.score.toInt(),
                scoreAdversary = it.scoreAdversary.toInt()
            )
        }
    }
}

object GameModelToDomainMapper : BaseMapper<GameModel, GameEntity>() {

    override fun transformFrom(source: GameEntity): GameModel = GameModel(
        id = source.id,
        gamerId = source.gamerId,
        adversary = source.adversary,
        score = source.score.toInt(),
        scoreAdversary = source.scoreAdversary.toInt()
    )

    override fun transformTo(source: GameModel): GameEntity =
        createScoreEntity(source)

    private fun createScoreEntity(it: GameModel) = GameEntity(
        id = it.id,
        gamerId = it.gamerId,
        adversary = it.adversary,
        score = it.score.toString(),
        scoreAdversary = it.scoreAdversary.toString(),
        result = "${it.score} x ${it.scoreAdversary}",
        isWinner = it.score > it.scoreAdversary
    )
}