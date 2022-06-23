package com.rosseti.domain.repository

import com.rosseti.domain.entity.PlayerEntity
import io.reactivex.Single

interface GamerRepository {
    fun fetchPlayers(): Single<List<PlayerEntity>>
    fun updatePlayer(id: String, name: String): Single<PlayerEntity>
    fun createPlayer(name: String): Single<PlayerEntity>
}