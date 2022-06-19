package com.rosseti.tmgfoosball.ui.detail

import com.rosseti.domain.entity.GamerEntity

sealed class GamerDetailsViewState {
    object ShowLoadingState : GamerDetailsViewState()
    data class ShowContent(val gamer: GamerEntity) : GamerDetailsViewState()
    data class ShowNetworkError(var error: Throwable): GamerDetailsViewState()
}