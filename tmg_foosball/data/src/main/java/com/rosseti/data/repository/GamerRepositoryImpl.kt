package com.rosseti.data.repository

import com.rosseti.data.api.Api
import com.rosseti.data.model.PlayerModel
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.repository.GamerRepository
import io.reactivex.Single

class GamerRepositoryImpl(private val api: Api) : GamerRepository {
    override fun fetchPlayers(): Single<List<PlayerEntity>> =
        api.fetchPlayers().map { it.map { player -> PlayerEntity(id = player.id, name = player.name) } }

    override fun updatePlayer(
        id: String,
        name: String
    ): Single<PlayerEntity> =
        api.updatePlayer(id, name)
            .map {
                createGamerEntity(it)
            }

    override fun createPlayer(
        name: String
    ): Single<PlayerEntity> =
        api.createPlayer(name)
            .map {
                createGamerEntity(it)
            }

    private fun createGamerEntity(it: PlayerModel) = PlayerEntity(
        id = it.id,
        name = it.name
    )
}
