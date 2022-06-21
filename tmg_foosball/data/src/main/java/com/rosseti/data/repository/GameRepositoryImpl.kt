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
                    GameEntity(
                        id = model.id,
                        gamerId = model.gamerId,
                        adversary = model.adversary,
                        score = model.score.toString(),
                        scoreAdversary = model.scoreAdversary.toString()
                    )
                }
            }
            .subscribeOn(Schedulers.io())
    }

    override fun updateGame(
        id: String,
        gamerId: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ): Single<GameEntity> =
        api.updateGame(
            id,
            gamerId,
            adversary,
            score,
            scoreAdversary
        )
            .map {
                createGameEntity(it)
            }

    override fun createGame(
        gamerId: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ): Single<GameEntity> =
        api.createGame(
            gamerId,
            adversary,
            score,
            scoreAdversary
        )
            .map {
                createGameEntity(it)
            }

    private fun createGameEntity(it: GameModel) = GameEntity(
        id = it.id,
        adversary = it.adversary,
        score = it.score.toString(),
        scoreAdversary = it.scoreAdversary.toString(),
        result = "${it.score} x ${it.scoreAdversary}",
        isWinner = it.score > it.scoreAdversary
    )
}
