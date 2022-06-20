package com.rosseti.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.rosseti.data.api.Api
import com.rosseti.data.model.GameModel
import com.rosseti.data.paging.GameDataSource
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.repository.GameRepository
import io.reactivex.Flowable
import io.reactivex.Single

class GameRepositoryImpl(private val api: Api) : GameRepository {
    override fun fetchGames(): Flowable<PagingData<GameEntity>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 20
            )
        ) {
            GameDataSource(api)
        }.flowable

        return pager
    }

    override fun updateGame(
        id: String,
        gamerId: String,
        adversary: String,
        score: Int,
        scoreAdversary: Int
    ): Single<GameEntity> =
        api.updateGame(
            id,
            gamerId,
            adversary,
            score,
            scoreAdversary)
            .map {
                createGameEntity(it)
            }

    override fun createGame(
        gamerId: String,
        adversary: String,
        score: Int,
        scoreAdversary: Int
    ): Single<GameEntity> =
        api.createGame(
            gamerId,
            adversary,
            score,
            scoreAdversary)
            .map {
                createGameEntity(it)
            }

    private fun createGameEntity(it: GameModel) = GameEntity(
        id = it.id,
        adversary = it.adversary,
        score = it.score,
        scoreAdversary = it.scoreAdversary,
        result = "${it.score} x ${it.scoreAdversary}",
        isWinner = it.score > it.scoreAdversary
    )
}
