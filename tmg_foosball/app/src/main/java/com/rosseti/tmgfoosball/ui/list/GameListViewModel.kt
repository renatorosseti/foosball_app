package com.rosseti.tmgfoosball.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.usecase.GetPlayersUseCase
import com.rosseti.domain.usecase.GetGamesUseCase
import com.rosseti.tmgfoosball.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class GameListViewModel @Inject constructor(
    private val getPlayersUseCase: GetPlayersUseCase,
    private val getGamesUseCase: GetGamesUseCase
) :
    BaseViewModel() {

    val response = MutableLiveData<GamerListViewState>()

    fun getPlayerList() {
        response.postValue(GamerListViewState.ShowLoadingState)
        val result = getPlayersUseCase()
        result.subscribeBy(onSuccess = {
            getGameList(it)
        }, onError = { e ->
            response.postValue(GamerListViewState.ShowNetworkError(e))
        }).addTo(compositeDisposable)
    }

    private fun getGameList(players: List<PlayerEntity>) {
        response.postValue(GamerListViewState.ShowLoadingState)
        val result = getGamesUseCase()
        result.subscribeBy(onSuccess = {
            val playersUpdated = players.map { player ->
                val games = it.filter { it.gamerId == player.id }
                player.copy(games = games, matches = games.size.toString(), scores = games.filter { it.score > it.scoreAdversary}.size.toString() )
            }
            response.postValue(GamerListViewState.ShowContentFeed(playersUpdated))
        }, onError = { e ->
            response.postValue(GamerListViewState.ShowNetworkError(e))
        }).addTo(compositeDisposable)
    }
}