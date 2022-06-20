package com.rosseti.tmgfoosball.ui.detail

import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.GamerEntity

sealed class GamerDetailsViewState {
    object ShowLoadingState : GamerDetailsViewState()
    data class ShowContent(val gamer: GamerEntity, val game: GameEntity) : GamerDetailsViewState()
    data class ShowNetworkError(var error: Throwable): GamerDetailsViewState()
}