package com.rosseti.tmgfoosball.ui.list

import androidx.lifecycle.MutableLiveData
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.usecase.GetGamesUseCase
import com.rosseti.domain.usecase.GetPlayersUseCase
import com.rosseti.tmgfoosball.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class PlayerListViewModel @Inject constructor(
    private val getPlayersUseCase: GetPlayersUseCase,
    private val getGamesUseCase: GetGamesUseCase
) :
    BaseViewModel() {

    val response = MutableLiveData<PlayerListViewState>()

    fun getPlayerList() {
        response.postValue(PlayerListViewState.ShowLoadingState)
        val result = getPlayersUseCase()
        result.subscribeBy(onSuccess = {
            updatePlayerGames(it)
        }, onError = { e ->
            response.postValue(PlayerListViewState.ShowNetworkError(e))
        }).addTo(compositeDisposable)
    }

    private fun updatePlayerGames(players: List<PlayerEntity>) {
        response.postValue(PlayerListViewState.ShowLoadingState)
        val result = getGamesUseCase(players)
        result.subscribeBy(onSuccess = { games ->
            response.postValue(PlayerListViewState.ShowContentFeed(games))
        }, onError = { e ->
            response.postValue(PlayerListViewState.ShowNetworkError(e))
        }).addTo(compositeDisposable)
    }
}