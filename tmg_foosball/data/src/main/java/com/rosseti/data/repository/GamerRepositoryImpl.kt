package com.rosseti.data.repository

import com.rosseti.data.api.Api
import com.rosseti.data.model.GamerModel
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.repository.GamerRepository
import io.reactivex.Single

class GamerRepositoryImpl(private val api: Api) : GamerRepository {
    override fun fetchPlayers(): Single<List<PlayerEntity>> =
        api.fetchPlayers().map { it.map { player -> PlayerEntity(id = player.id, name = player.name) } }

    override fun updatePlayer(
        scoreId: Int,
        name: String
    ): Single<PlayerEntity> =
        api.updateScore(scoreId, name)
            .map {
                createGamerEntity(it)
            }

    override fun createPlayer(
        name: String
    ): Single<PlayerEntity> =
        api.createScore(name)
            .map {
                createGamerEntity(it)
            }

    private fun createGamerEntity(it: GamerModel) = PlayerEntity(
        id = it.id,
        name = it.name
    )
}
