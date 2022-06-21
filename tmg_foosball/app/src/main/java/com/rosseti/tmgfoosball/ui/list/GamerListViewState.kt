package com.rosseti.tmgfoosball.ui.list

import com.rosseti.domain.entity.PlayerEntity

sealed class GamerListViewState {
    object ShowLoadingState: GamerListViewState()
    data class ShowContentFeed(val players: List<PlayerEntity>): GamerListViewState()
    data class ShowNetworkError(var error: Throwable): GamerListViewState()
}