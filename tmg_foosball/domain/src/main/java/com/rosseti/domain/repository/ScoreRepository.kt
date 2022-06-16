package com.rosseti.domain.repository

import androidx.paging.PagingData
import com.rosseti.domain.entity.ScoreEntity
import io.reactivex.Flowable

interface ScoreRepository {
    fun fetchScores(): Flowable<PagingData<ScoreEntity>>
}