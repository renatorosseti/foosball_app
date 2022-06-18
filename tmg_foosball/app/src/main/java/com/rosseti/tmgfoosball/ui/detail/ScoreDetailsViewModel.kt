package com.rosseti.tmgfoosball.ui.detail

import androidx.lifecycle.MutableLiveData
import com.rosseti.domain.entity.ScoreEntity
import com.rosseti.domain.usecase.CreateScoreUseCase
import com.rosseti.domain.usecase.UpdateScoreUseCase
import com.rosseti.tmgfoosball.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ScoreDetailsViewModel @Inject constructor(
    private val updateScoreUseCase: UpdateScoreUseCase,
    private val createScoreUseCase: CreateScoreUseCase
) : BaseViewModel() {

    val response = MutableLiveData<ScoreDetailsViewState>()

    var scoreDetail = MutableLiveData<ScoreEntity>()

    fun requestScore(name: String, matches: String, scores: String) {
        if (scoreDetail.value == null) {
            createScore(name, matches, scores)
        } else {
            updateScore(name, matches, scores)
        }
    }

    fun updateScoreEntity(entity: ScoreEntity) = scoreDetail.postValue(entity)

    private fun updateScore(name: String, matches: String, scores: String) {
        response.postValue(ScoreDetailsViewState.ShowLoadingState)
        updateScoreUseCase(scoreDetail.value?.id ?: 0, name, matches, scores)
            .subscribeBy(onSuccess = {
                updateScoreEntity(it)
                response.postValue(ScoreDetailsViewState.ShowContent(it))
            }, onError = { e ->
                response.postValue(ScoreDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }

    private fun createScore(name: String, matches: String, scores: String) {
        response.postValue(ScoreDetailsViewState.ShowLoadingState)
        createScoreUseCase(name, matches, scores)
            .subscribeBy(onSuccess = {
                response.postValue(ScoreDetailsViewState.ShowContent(it))
                updateScoreEntity(it)
            }, onError = { e ->
                response.postValue(ScoreDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }
}