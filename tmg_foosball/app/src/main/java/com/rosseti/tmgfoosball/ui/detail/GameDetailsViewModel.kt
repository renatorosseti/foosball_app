package com.rosseti.tmgfoosball.ui.detail

import androidx.lifecycle.MutableLiveData
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.extensions.updateGames
import com.rosseti.domain.usecase.CreateGameUseCase
import com.rosseti.domain.usecase.CreatePlayerUseCase
import com.rosseti.domain.usecase.UpdateGameUseCase
import com.rosseti.tmgfoosball.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class GameDetailsViewModel @Inject constructor(
    private val createPlayerUseCase: CreatePlayerUseCase,
    private val updateGameUseCase: UpdateGameUseCase,
    private val createGameUseCase: CreateGameUseCase
) : BaseViewModel() {

    val response = MutableLiveData<GameDetailsViewState>()

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
            }
        }
    }

    fun updatePlayerEntity(entity: PlayerEntity) {
        playerDetail = entity
    }

    fun updateGameEntity(entity: GameEntity) {
        gameDetail = entity
    }

    fun updateGame(
        id: String,
        gamerId: String,
        adversaryId: String,
        adversary: String,
        playerName: String,
        score: String,
        scoreAdversary: String
    ) {
        response.postValue(GameDetailsViewState.ShowLoadingState)
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
                response.postValue(GameDetailsViewState.ShowContent(playerDetail, it))
                updateGameEntity(it)
                updatePlayerEntity(playerDetail.updateGames(it))
            }, onError = { e ->
                response.postValue(GameDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }

    fun createGame(
        playerId: String,
        adversaryId: String,
        playerName: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ) {
        response.postValue(GameDetailsViewState.ShowLoadingState)
        createGameUseCase(playerId, adversaryId, playerName, adversary, score, scoreAdversary)
            .subscribeBy(onSuccess = {
                response.postValue(GameDetailsViewState.ShowContent(playerDetail, it))
                updateGameEntity(it)
                updatePlayerEntity(playerDetail.updateGames(it))
            }, onError = { e ->
                response.postValue(GameDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }

    fun createPlayer(
        playerName: String,
        adversaryId: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ) {
        response.postValue(GameDetailsViewState.ShowLoadingState)
        createPlayerUseCase(playerName)
            .subscribeBy(onSuccess = {
                response.postValue(GameDetailsViewState.ShowContent(it, gameDetail))
                updatePlayerEntity(it)
                if (adversary.isNotEmpty()) {
                    createGame(playerDetail.id, adversaryId, playerName, adversary, score, scoreAdversary)
                }
            }, onError = { e ->
                response.postValue(GameDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }
}