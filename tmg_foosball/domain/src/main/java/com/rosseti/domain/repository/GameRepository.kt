package com.rosseti.domain.repository

import androidx.paging.PagingData
import com.rosseti.domain.entity.GameEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface GameRepository {
    fun fetchGames(): Flowable<PagingData<GameEntity>>
    fun updateGame(
        id: String,
        gamerId: String,
        adversary: String,
        score: Int,
        scoreAdversary: Int
    ): Single<GameEntity>

    fun createGame(
        gamerId: String,
        adversary: String,
        score: Int,
        scoreAdversary: Int
    ): Single<GameEntity>
}