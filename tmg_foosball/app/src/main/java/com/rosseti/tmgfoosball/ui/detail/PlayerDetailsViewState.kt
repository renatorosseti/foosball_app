package com.rosseti.tmgfoosball.ui.detail

import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.PlayerEntity

sealed class PlayerDetailsViewState {
    object ShowLoadingState : PlayerDetailsViewState()
    data class ShowContent(val player: PlayerEntity, val game: GameEntity) : PlayerDetailsViewState()
    data class ShowNetworkError(var error: Throwable): PlayerDetailsViewState()
}