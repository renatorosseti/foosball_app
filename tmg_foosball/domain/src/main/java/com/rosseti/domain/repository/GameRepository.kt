package com.rosseti.domain.repository

import com.rosseti.domain.entity.GameEntity
import io.reactivex.Single

interface GameRepository {
    fun fetchGames(): Single<List<GameEntity>>
    fun updateGame(
        id: String,
        gamerId: String,
        adversaryId: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ): Single<GameEntity>

    fun createGame(
        gamerId: String,
        adversaryId: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ): Single<GameEntity>
}