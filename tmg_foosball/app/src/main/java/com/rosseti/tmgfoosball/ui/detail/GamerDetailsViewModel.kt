package com.rosseti.tmgfoosball.ui.detail

import androidx.lifecycle.MutableLiveData
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.GamerEntity
import com.rosseti.domain.usecase.CreateGameUseCase
import com.rosseti.domain.usecase.CreateGamerUseCase
import com.rosseti.domain.usecase.UpdateGameUseCase
import com.rosseti.domain.usecase.UpdateGamerUseCase
import com.rosseti.tmgfoosball.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class GamerDetailsViewModel @Inject constructor(
    private val updateGamerUseCase: UpdateGamerUseCase,
    private val createGamerUseCase: CreateGamerUseCase,
    private val updateGameUseCase: UpdateGameUseCase,
    private val createGameUseCase: CreateGameUseCase
) : BaseViewModel() {

    val response = MutableLiveData<GamerDetailsViewState>()

    var gamerDetail: GamerEntity = GamerEntity()

    var gameDetail: GameEntity = GameEntity()

    fun requestNewGame(name: String, adversary: String, score: Int, scoreAdversary: Int) {
        when {
            gamerDetail.id.isEmpty() -> {
                createGamer(name, adversary, score, scoreAdversary)
            }
            gameDetail.id.isEmpty() -> {
                createGame(gamerDetail.id, adversary, score, scoreAdversary)
            }
            else -> {
                updateGame(gamerDetail.id, gamerDetail.id, adversary, score, scoreAdversary)
            }
        }
    }

    fun updateGamerEntity(entity: GamerEntity) {
        gamerDetail = entity
    }

    fun updateGameEntity(entity: GameEntity) {
        gameDetail = entity
    }

    private fun updateGame(
        id: String,
        gamerId: String,
        adversary: String,
        score: Int,
        scoreAdversary: Int
    ) {
        response.postValue(GamerDetailsViewState.ShowLoadingState)
        updateGameUseCase(id, adversary, gamerId, score, scoreAdversary)
            .subscribeBy(onSuccess = {
                response.postValue(GamerDetailsViewState.ShowContent(gamerDetail, it))
                updateGameEntity(it)
            }, onError = { e ->
                response.postValue(GamerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }

    private fun createGame(gamerId: String, adversary: String, score: Int, scoreAdversary: Int) {
        response.postValue(GamerDetailsViewState.ShowLoadingState)
        createGameUseCase(gamerId, adversary, score, scoreAdversary)
            .subscribeBy(onSuccess = {
                response.postValue(GamerDetailsViewState.ShowContent(gamerDetail, it))
                updateGameEntity(it)
            }, onError = { e ->
                response.postValue(GamerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }

    private fun updateGamer(id: Int, name: String) {
        response.postValue(GamerDetailsViewState.ShowLoadingState)
        updateGamerUseCase(id, name)
            .subscribeBy(onSuccess = {
                updateGamerEntity(it)
                response.postValue(GamerDetailsViewState.ShowContent(it, gameDetail))
            }, onError = { e ->
                response.postValue(GamerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }

    private fun createGamer(name: String, adversary: String, score: Int, scoreAdversary: Int) {
        response.postValue(GamerDetailsViewState.ShowLoadingState)
        createGamerUseCase(name)
            .subscribeBy(onSuccess = {
                response.postValue(GamerDetailsViewState.ShowContent(it, gameDetail))
                updateGamerEntity(it)
                if (adversary.isNotEmpty()) {
                    createGame(gamerDetail.id, adversary, score, scoreAdversary)
                }
            }, onError = { e ->
                response.postValue(GamerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }
}