package com.rosseti.tmgfoosball.ui.list

import androidx.lifecycle.MutableLiveData
import com.rosseti.domain.entity.GameEntity
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
           /* val list = players.map { player ->
                val games2 = games.filter { it.gamerId == player.id || it.adversaryId == player.id }
                player.copy(
                    games = games2,
                    matches = games2.size.toString(),
                    scores = games2.filter { it.score > it.scoreAdversary }.size.toString()
                )
            }*/
            response.postValue(PlayerListViewState.ShowContentFeed(games))
        }, onError = { e ->
            response.postValue(PlayerListViewState.ShowNetworkError(e))
        }).addTo(compositeDisposable)
    }

    private fun updatePlayers(player: PlayerEntity, games: List<GameEntity>): PlayerEntity {
        player.copy(
            games = games.filter { it.playerId == player.id || it.adversaryId == player.id },
            matches = games.size.toString(),
            scores = games.filter { it.score > it.scoreAdversary }.size.toString()
        )
        return player
    }
}