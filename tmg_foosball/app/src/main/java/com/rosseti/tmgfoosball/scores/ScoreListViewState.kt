package com.rosseti.tmgfoosball.scores

import androidx.paging.PagingData
import com.rosseti.domain.entity.NetworkException
import com.rosseti.domain.entity.ScoreEntity

sealed class ScoreListViewState {
    object ShowLoadingState: ScoreListViewState()
    data class ShowContentFeed(val scores: PagingData<ScoreEntity>): ScoreListViewState()
    data class ShowNetworkError(val message: Int, var networkException: NetworkException = NetworkException(Throwable())): ScoreListViewState()
}