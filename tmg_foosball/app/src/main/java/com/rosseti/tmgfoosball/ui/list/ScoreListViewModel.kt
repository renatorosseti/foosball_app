package com.rosseti.tmgfoosball.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava2.cachedIn
import com.rosseti.domain.usecase.GetScoresUseCase
import com.rosseti.tmgfoosball.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ScoreListViewModel @Inject constructor(private val getScoresUseCase: GetScoresUseCase) :
    BaseViewModel() {

    val response = MutableLiveData<ScoreListViewState>()

    fun getScoreList() {
        response.postValue(ScoreListViewState.ShowLoadingState)
        val result = getScoresUseCase()
            .cachedIn(viewModelScope)
        result.subscribeBy(onNext = {
            response.postValue(ScoreListViewState.ShowContentFeed(it))
        }, onError = { e ->
            response.postValue(ScoreListViewState.ShowNetworkError(e))
        }).addTo(compositeDisposable)
    }
}