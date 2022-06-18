package com.rosseti.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.rosseti.data.api.Api
import com.rosseti.data.paging.ScoreDataSource
import com.rosseti.domain.entity.ScoreEntity
import com.rosseti.domain.repository.ScoreRepository
import io.reactivex.Flowable
import io.reactivex.Single

class ScoreRepositoryImpl(private val api: Api) : ScoreRepository {
    override fun fetchScores(): Flowable<PagingData<ScoreEntity>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 20
            )
        ) {
            ScoreDataSource(api)
        }.flowable

        return pager
    }

    override fun updateScore(
        scoreId: Int,
        name: String,
        matches: String,
        scores: String
    ): Single<ScoreEntity> =
        api.updateScore(scoreId, name, matches, scores).map {
            ScoreEntity(id = it.id, name = it.name, matches = it.matches, scores = it.scores)
        }

    override fun createScore(
        name: String,
        matches: String,
        scores: String
    ): Single<ScoreEntity> =
        api.createScore(name, matches, scores).map {
            ScoreEntity(id = it.id, name = it.name, matches = it.matches, scores = it.scores)
        }
}