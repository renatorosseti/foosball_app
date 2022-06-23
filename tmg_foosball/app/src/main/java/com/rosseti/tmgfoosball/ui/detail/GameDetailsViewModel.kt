package com.rosseti.tmgfoosball.ui.detail

import androidx.lifecycle.MutableLiveData
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.extensions.updateGames
import com.rosseti.domain.usecase.CreateGameUseCase
import com.rosseti.domain.usecase.CreatePlayerUseCase
import com.rosseti.domain.usecase.UpdateGameUseCase
import com.rosseti.domain.usecase.UpdatePlayerUseCase
import com.rosseti.tmgfoosball.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class GameDetailsViewModel @Inject constructor(
    private val updatePlayerUseCase: UpdatePlayerUseCase,
    private val createPlayerUseCase: CreatePlayerUseCase,
    private val updateGameUseCase: UpdateGameUseCase,
    private val createGameUseCase: CreateGameUseCase
) : BaseViewModel() {

    val response = MutableLiveData<PlayerDetailsViewState>()

    var playerDetail: PlayerEntity = PlayerEntity()

    var gameDetail: GameEntity = GameEntity()

    fun requestNewGame(
        playerName: String,
        adversaryId: String,
        adversaryName: String,
        score: String,
        scoreAdversary: String
    ) {
        when {
            playerDetail.id.isEmpty() -> {
                createPlayer(playerName, adversaryId, adversaryName, score, scoreAdversary)
            }
            gameDetail.id.isEmpty() -> {
                createGame(playerDetail.id, adversaryId, playerName, adversaryName, score, scoreAdversary)
                if (playerDetail.name != playerName) {
                    updatePlayer(playerDetail.id, playerName)
                }
            }
            else -> {
                updateGame(
                    id = gameDetail.id,
                    gamerId = playerDetail.id,
                    adversaryId = adversaryId,
                    playerName = playerName,
                    adversary = adversaryName,
                    score = score,
                    scoreAdversary = scoreAdversary
                )
                if (playerDetail.name != playerName) {
                    updatePlayer(playerDetail.id, playerName)
                }
            }
        }
    }

    fun updatePlayerEntity(entity: PlayerEntity) {
        playerDetail = entity
    }

    fun updateGameEntity(entity: GameEntity) {
        gameDetail = entity
    }

    private fun updateGame(
        id: String,
        gamerId: String,
        adversaryId: String,
        adversary: String,
        playerName: String,
        score: String,
        scoreAdversary: String
    ) {
        response.postValue(PlayerDetailsViewState.ShowLoadingState)
        updateGameUseCase(
            id = id,
            gamerId = gamerId,
            adversaryId = adversaryId,
            playerName = playerName,
            adversaryName = adversary,
            score = score,
            scoreAdversary = scoreAdversary
        )
            .subscribeBy(onSuccess = {
                response.postValue(PlayerDetailsViewState.ShowContent(playerDetail, it))
                updateGameEntity(it)
                updatePlayerEntity(playerDetail.updateGames(it))
            }, onError = { e ->
                response.postValue(PlayerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }

    private fun createGame(
        gamerId: String,
        adversaryId: String,
        playerName: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ) {
        response.postValue(PlayerDetailsViewState.ShowLoadingState)
        createGameUseCase(gamerId, adversaryId, playerName, adversary, score, scoreAdversary)
            .subscribeBy(onSuccess = {
                response.postValue(PlayerDetailsViewState.ShowContent(playerDetail, it))
                updateGameEntity(it)
                updatePlayerEntity(playerDetail.updateGames(it))
            }, onError = { e ->
                response.postValue(PlayerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }

    private fun updatePlayer(id: String, name: String) {
        response.postValue(PlayerDetailsViewState.ShowLoadingState)
        updatePlayerUseCase(id, name)
            .subscribeBy(onSuccess = {
                updatePlayerEntity(it)
                response.postValue(PlayerDetailsViewState.ShowContent(it, gameDetail))
            }, onError = { e ->
                response.postValue(PlayerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }

    private fun createPlayer(
        playerName: String,
        adversaryId: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ) {
        response.postValue(PlayerDetailsViewState.ShowLoadingState)
        createPlayerUseCase(playerName)
            .subscribeBy(onSuccess = {
                response.postValue(PlayerDetailsViewState.ShowContent(it, gameDetail))
                updatePlayerEntity(it)
                if (adversary.isNotEmpty()) {
                    createGame(playerDetail.id, adversaryId, playerName, adversary, score, scoreAdversary)
                }
            }, onError = { e ->
                response.postValue(PlayerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }
}