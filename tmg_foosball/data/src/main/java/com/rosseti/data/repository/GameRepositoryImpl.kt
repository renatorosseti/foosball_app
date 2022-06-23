package com.rosseti.data.repository

import com.rosseti.data.api.Api
import com.rosseti.data.model.GameModel
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.repository.GameRepository
import io.reactivex.Single

class GameRepositoryImpl(private val api: Api) : GameRepository {
    override fun fetchGames(): Single<List<GameEntity>> {
        return api.fetchGames()
            .map {
                it.map { model ->
                    createGameEntity(model)
                }
            }
    }

    override fun updateGame(
        id: String,
        playerId: String,
        adversaryId: String,
        playerName: String,
        adversaryName: String,
        score: String,
        scoreAdversary: String
    ): Single<GameEntity> =
        api.updateGame(
            id = id,
            gamerId = playerId,
            adversaryId = adversaryId,
            playerName = playerName,
            adversary = adversaryName,
            score = score,
            scoreAdversary = scoreAdversary
        ).map {
            createGameEntity(it)
        }

    override fun createGame(
        playerId: String,
        adversaryId: String,
        playerName: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ): Single<GameEntity> =
        api.createGame(
            gamerId = playerId,
            adversaryId = adversaryId,
            playerName = playerName,
            adversary = adversary,
            score = score,
            scoreAdversary = scoreAdversary
        ).map {
            createGameEntity(it)
        }

    private fun createGameEntity(it: GameModel) = GameEntity(
        id = it.id,
        _playerId = it.playerId,
        _adversary = it.adversary,
        _adversaryId = it.adversaryId,
        _playerName = it.playerName,
        _score = it.score,
        _scoreAdversary = it.scoreAdversary
    )
}
