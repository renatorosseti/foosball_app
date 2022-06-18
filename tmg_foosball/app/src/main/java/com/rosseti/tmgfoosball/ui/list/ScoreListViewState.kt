package com.rosseti.tmgfoosball.ui.list

import androidx.paging.PagingData
import com.rosseti.domain.entity.ScoreEntity

sealed class ScoreListViewState {
    object ShowLoadingState: ScoreListViewState()
    data class ShowContentFeed(val scores: PagingData<ScoreEntity>): ScoreListViewState()
    data class ShowNetworkError(var error: Throwable): ScoreListViewState()
}