package com.rosseti.data.repository

import com.rosseti.data.api.Api
import com.rosseti.data.model.GameModel
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.repository.GameRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GameRepositoryImpl(private val api: Api) : GameRepository {
    override fun fetchGames(): Single<List<GameEntity>> {
        return api.fetchGames()
            .map {
                it.map { model ->
                    createGameEntity(model)
                }
            }
            .subscribeOn(Schedulers.io())
    }

    override fun updateGame(
        id: String,
        gamerId: String,
        adversaryId: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ): Single<GameEntity> =
        api.updateGame(
            id = id,
            gamerId = gamerId,
            adversaryId = adversaryId,
            adversary = adversary,
            score = score,
            scoreAdversary = scoreAdversary
        ).map {
            createGameEntity(it)
        }

    override fun createGame(
        gamerId: String,
        adversaryId: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ): Single<GameEntity> =
        api.createGame(
            gamerId = gamerId,
            adversaryId = adversaryId,
            adversary = adversary,
            score = score,
            scoreAdversary = scoreAdversary
        ).map {
            createGameEntity(it)
        }

    private fun createGameEntity(it: GameModel) = GameEntity(
        id = it.id,
        playerId = it.playerId,
        adversary = it.adversary,
        adversaryId = it.adversaryId,
        playerName = it.playerName,
        score = it.score,
        scoreAdversary = it.scoreAdversary,
        result = "${it.score} x ${it.scoreAdversary}",
        isWinner = it.score > it.scoreAdversary
    )
}
