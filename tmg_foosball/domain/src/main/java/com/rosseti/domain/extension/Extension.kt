package com.rosseti.domain.extension

import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.PlayerEntity

fun PlayerEntity.getAdversaries(
    players: List<PlayerEntity>
): HashMap<String, String> {
    val hashMap = HashMap<String, String>()
    players
        .filter { it.id != id }
        .map { hashMap[it.id] = it.name }
    return hashMap
}

fun PlayerEntity.updateGames(
    game: GameEntity
): PlayerEntity {
    val gameFromPlayer = this.games.find{ it.id == game.id }
    val player = if (gameFromPlayer == null) {
         this.copy(games = this.games + game)
    } else {
        val updatedGames = this.games.map {
            if (it.id == game.id) game
            else it
        }
        this.copy(games = updatedGames)
    }
    return player
}
