package com.rosseti.tmgfoosball.scores

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rosseti.domain.entity.NetworkException
import com.rosseti.domain.usecase.CreateScoreUseCase
import com.rosseti.domain.usecase.GetScoreDetailsUseCase
import com.rosseti.domain.usecase.UpdateScoreUseCase
import com.rosseti.tmgfoosball.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ScoreDetailsViewModel @Inject constructor(
    private val getScoreDetailsUseCase: GetScoreDetailsUseCase,
    private val updateScoreUseCase: UpdateScoreUseCase,
    private val createScoreUseCase: CreateScoreUseCase
) : BaseViewModel() {

    val response = MutableLiveData<ScoreDetailsViewState>()

    fun getScoreDetailById(scoreId: String) {
        response.postValue(ScoreDetailsViewState.ShowLoadingState)
        val result = getScoreDetailsUseCase(scoreId)
            .subscribeBy(onSuccess = {
                response.postValue(ScoreDetailsViewState.ShowContent(it))
                Log.i("ScoreViewModel", "Response $it.")
            }, onError = { e ->
                response.postValue(ScoreDetailsViewState.ShowNetworkError(404, NetworkException(e)))
            }).addTo(compositeDisposable)
    }

    fun updateScore(scoreId: String, name: String, matches: String, scores: String) {
        response.postValue(ScoreDetailsViewState.ShowLoadingState)
        val result = updateScoreUseCase(scoreId, name, matches, scores)
            .subscribeBy(onSuccess = {
                response.postValue(ScoreDetailsViewState.ShowContent(it))
                Log.i("ScoreViewModel", "Response $it.")
            }, onError = { e ->
                response.postValue(ScoreDetailsViewState.ShowNetworkError(404, NetworkException(e)))
            }).addTo(compositeDisposable)
    }

    fun createScore(name: String, matches: String, scores: String) {
        response.postValue(ScoreDetailsViewState.ShowLoadingState)
        val result = createScoreUseCase(name, matches, scores)
            .subscribeBy(onSuccess = {
                response.postValue(ScoreDetailsViewState.ShowContent(it))
                Log.i("ScoreViewModel", "Response $it.")
            }, onError = { e ->
                response.postValue(ScoreDetailsViewState.ShowNetworkError(404, NetworkException(e)))
            }).addTo(compositeDisposable)
    }
}