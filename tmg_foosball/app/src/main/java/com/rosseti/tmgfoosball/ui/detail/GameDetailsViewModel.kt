package com.rosseti.tmgfoosball.ui.detail

import androidx.lifecycle.MutableLiveData
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.usecase.CreateGameUseCase
import com.rosseti.domain.usecase.CreateGamerUseCase
import com.rosseti.domain.usecase.UpdateGameUseCase
import com.rosseti.domain.usecase.UpdateGamerUseCase
import com.rosseti.tmgfoosball.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class GameDetailsViewModel @Inject constructor(
    private val updateGamerUseCase: UpdateGamerUseCase,
    private val createGamerUseCase: CreateGamerUseCase,
    private val updateGameUseCase: UpdateGameUseCase,
    private val createGameUseCase: CreateGameUseCase
) : BaseViewModel() {

    val response = MutableLiveData<PlayerDetailsViewState>()

    var playerDetail: PlayerEntity = PlayerEntity()

    var gameDetail: GameEntity = GameEntity()

    fun requestNewGame(name: String, adversaryId: String, adversary: String, score: String, scoreAdversary: String) {
        when {
            playerDetail.id.isEmpty() -> {
                createGamer(name, adversaryId, adversary, score, scoreAdversary)
            }
            gameDetail.id.isEmpty() -> {
                createGame(playerDetail.id,adversaryId, adversary, score, scoreAdversary)
            }
            else -> {
                updateGame(id = gameDetail.id, gamerId = playerDetail.id, adversaryId = adversaryId, adversary = adversary, score = score, scoreAdversary = scoreAdversary)
            }
        }
    }

    fun updateGamerEntity(entity: PlayerEntity) {
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
        score: String,
        scoreAdversary: String
    ) {
        response.postValue(PlayerDetailsViewState.ShowLoadingState)
        updateGameUseCase(id = id, gamerId = gamerId, adversaryId = adversaryId, adversary = adversary, score = score, scoreAdversary = scoreAdversary)
            .subscribeBy(onSuccess = {
                response.postValue(PlayerDetailsViewState.ShowContent(playerDetail, it))
                updateGameEntity(it)
            }, onError = { e ->
                response.postValue(PlayerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }

    private fun createGame(
        gamerId: String,
        adversaryId: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ) {
        response.postValue(PlayerDetailsViewState.ShowLoadingState)
        createGameUseCase(gamerId, adversaryId, adversary, score, scoreAdversary)
            .subscribeBy(onSuccess = {
                response.postValue(PlayerDetailsViewState.ShowContent(playerDetail, it))
                updateGameEntity(it)
            }, onError = { e ->
                response.postValue(PlayerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }

    private fun updateGamer(id: Int, name: String) {
        response.postValue(PlayerDetailsViewState.ShowLoadingState)
        updateGamerUseCase(id, name)
            .subscribeBy(onSuccess = {
                updateGamerEntity(it)
                response.postValue(PlayerDetailsViewState.ShowContent(it, gameDetail))
            }, onError = { e ->
                response.postValue(PlayerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }

    private fun createGamer(
        name: String,
        adversaryId: String,
        adversary: String,
        score: String,
        scoreAdversary: String
    ) {
        response.postValue(PlayerDetailsViewState.ShowLoadingState)
        createGamerUseCase(name)
            .subscribeBy(onSuccess = {
                response.postValue(PlayerDetailsViewState.ShowContent(it, gameDetail))
                updateGamerEntity(it)
                if (adversary.isNotEmpty()) {
                    createGame(playerDetail.id, adversaryId, adversary, score, scoreAdversary)
                }
            }, onError = { e ->
                response.postValue(PlayerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }
}