package com.rosseti.tmgfoosball.scores

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava2.cachedIn
import com.rosseti.domain.entity.NetworkException
import com.rosseti.domain.usecase.GetScoresUseCase
import com.rosseti.tmgfoosball.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ScoreViewModel @Inject constructor(private val getScoresUseCase: GetScoresUseCase) : BaseViewModel() {

    val response = MutableLiveData<ScoreListViewState>()

    init {

    }

    fun getScoreList(isRefresh: Boolean = true) {
        response.postValue(ScoreListViewState.ShowLoadingState)
        val result = getScoresUseCase()
            .cachedIn(viewModelScope)
        result.subscribeBy(onNext = {
            response.postValue(ScoreListViewState.ShowContentFeed(it))
            Log.i("ScoreViewModel", "Response $it.")
        }, onError = { e ->
            response.postValue(ScoreListViewState.ShowNetworkError(404, NetworkException(e)))
        }).addTo(compositeDisposable)
    }
}