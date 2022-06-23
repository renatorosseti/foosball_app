package com.rosseti.domain.repository

import com.rosseti.domain.entity.GameEntity
import io.reactivex.Single

interface GameRepository {
    fun fetchGames(): Single<List<GameEntity>>

    fun updateGame(
        id: String,
        playerId: String,
        adversaryId: String,
        playerName: String,
        adversaryName: String,
        score: String,
        scoreAdversary: String
    ): Single<GameEntity>

    fun createGame(
        playerId: String,
        adversaryId: String,
        playerName: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ): Single<GameEntity>
}