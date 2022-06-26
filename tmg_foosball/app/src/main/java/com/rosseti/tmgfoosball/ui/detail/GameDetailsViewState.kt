package com.rosseti.tmgfoosball.ui.detail

import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.PlayerEntity

sealed class GameDetailsViewState {
    object ShowLoadingState : GameDetailsViewState()
    data class ShowContent(val player: PlayerEntity, val game: GameEntity) : GameDetailsViewState()
    data class ShowNetworkError(val error: Throwable): GameDetailsViewState()
}