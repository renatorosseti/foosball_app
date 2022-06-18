package com.rosseti.tmgfoosball.ui.detail

import com.rosseti.domain.entity.ScoreEntity

sealed class ScoreDetailsViewState {
    object ShowLoadingState : ScoreDetailsViewState()
    data class ShowContent(val score: ScoreEntity) : ScoreDetailsViewState()
    data class ShowNetworkError(var error: Throwable): ScoreDetailsViewState()
}