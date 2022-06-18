package com.rosseti.domain.repository

import androidx.paging.PagingData
import com.rosseti.domain.entity.ScoreEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface ScoreRepository {
    fun fetchScores(): Flowable<PagingData<ScoreEntity>>
    fun fetchScoreById(scoreId: Int): Single<ScoreEntity>
    fun updateScore(scoreId: Int, name: String, matches: String, scores: String): Single<ScoreEntity>
    fun createScore(name: String, matches: String, scores: String): Single<ScoreEntity>
}