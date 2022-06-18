package com.rosseti.tmgfoosball.scores

import com.rosseti.domain.entity.NetworkException
import com.rosseti.domain.entity.ScoreEntity

sealed class ScoreDetailsViewState {
    object ShowLoadingState : ScoreDetailsViewState()
    data class ShowContent(val score: ScoreEntity) : ScoreDetailsViewState()
    data class ShowNetworkError(
        val message: Int,
        var networkException: NetworkException = NetworkException(Throwable())
    ) : ScoreDetailsViewState()
}