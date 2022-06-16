package com.rosseti.tmgfoosball.scores

import androidx.paging.PagingData
import com.rosseti.domain.entity.NetworkException
import com.rosseti.domain.entity.ScoreEntity

sealed class ScoreViewState {
    object ShowLoadingState: ScoreViewState()
    data class ShowContentFeed(val scores: PagingData<ScoreEntity>): ScoreViewState()
    data class ShowNetworkError(val message: Int, var networkException: NetworkException = NetworkException(Throwable())): ScoreViewState()
}