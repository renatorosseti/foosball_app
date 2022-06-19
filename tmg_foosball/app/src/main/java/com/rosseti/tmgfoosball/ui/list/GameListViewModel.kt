package com.rosseti.tmgfoosball.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava2.cachedIn
import com.rosseti.domain.usecase.GetGamersUseCase
import com.rosseti.tmgfoosball.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class GameListViewModel @Inject constructor(private val getGamersUseCase: GetGamersUseCase) :
    BaseViewModel() {

    val response = MutableLiveData<GamerListViewState>()

    fun getScoreList() {
        response.postValue(GamerListViewState.ShowLoadingState)
        val result = getGamersUseCase()
            .cachedIn(viewModelScope)
        result.subscribeBy(onNext = {
            response.postValue(GamerListViewState.ShowContentFeed(it))
        }, onError = { e ->
            response.postValue(GamerListViewState.ShowNetworkError(e))
        }).addTo(compositeDisposable)
    }
}