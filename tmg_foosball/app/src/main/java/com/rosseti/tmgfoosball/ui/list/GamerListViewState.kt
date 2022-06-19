package com.rosseti.tmgfoosball.ui.list

import androidx.paging.PagingData
import com.rosseti.domain.entity.GamerEntity

sealed class GamerListViewState {
    object ShowLoadingState: GamerListViewState()
    data class ShowContentFeed(val scores: PagingData<GamerEntity>): GamerListViewState()
    data class ShowNetworkError(var error: Throwable): GamerListViewState()
}