package com.rosseti.tmgfoosball.ui.detail

import androidx.lifecycle.MutableLiveData
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.GamerEntity
import com.rosseti.domain.usecase.CreateGamerUseCase
import com.rosseti.domain.usecase.UpdateGamerUseCase
import com.rosseti.tmgfoosball.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class GamerDetailsViewModel @Inject constructor(
    private val updateGamerUseCase: UpdateGamerUseCase,
    private val createGamerUseCase: CreateGamerUseCase
) : BaseViewModel() {

    val response = MutableLiveData<GamerDetailsViewState>()

    var gamerDetail: GamerEntity? = null

    var gameDetail: GameEntity? = null

    fun requestNewGame(name: String, game: GameEntity?) {
        if (game != null) {
            if (gamerDetail != null) {
                if (gamerDetail?.games?.contains(game) == false) {
                    gamerDetail?.games?.add(game)
                } else {
                    gamerDetail?.games?.filter { it.id == game.id }?.forEach {
                        it.copy(
                            adversary = game.adversary,
                            score = game.score,
                            scoreAdversary = game.scoreAdversary
                        )
                    }
                }
            }

            if (gamerDetail == null) createGamer(name, gamerDetail?.games ?: listOf())
            else updateGamer(gamerDetail?.id ?: 0, name, gamerDetail?.games ?: listOf())
        }
    }

    fun updateGamerEntity(entity: GamerEntity) {
        gamerDetail = entity
    }

    fun updateGameEntity(entity: GameEntity) {
        gameDetail = entity
    }

    private fun updateGamer(id: Int, name: String, games: List<GameEntity>) {
        response.postValue(GamerDetailsViewState.ShowLoadingState)
        updateGamerUseCase(id, name, games)
            .subscribeBy(onSuccess = {
                updateGamerEntity(it)
                response.postValue(GamerDetailsViewState.ShowContent(it))
            }, onError = { e ->
                response.postValue(GamerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }

    private fun createGamer(name: String, games: List<GameEntity>) {
        response.postValue(GamerDetailsViewState.ShowLoadingState)
        createGamerUseCase(name, games)
            .subscribeBy(onSuccess = {
                response.postValue(GamerDetailsViewState.ShowContent(it))
                updateGamerEntity(it)
            }, onError = { e ->
                response.postValue(GamerDetailsViewState.ShowNetworkError(e))
            }).addTo(compositeDisposable)
    }
}