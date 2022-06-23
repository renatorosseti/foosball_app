package com.rosseti.tmgfoosball.ui.list

import com.rosseti.domain.entity.PlayerEntity

sealed class PlayerListViewState {
    object ShowLoadingState: PlayerListViewState()
    data class ShowContentFeed(val players: List<PlayerEntity>): PlayerListViewState()
    data class ShowNetworkError(var error: Throwable): PlayerListViewState()
}