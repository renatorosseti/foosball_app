package com.rosseti.domain.usecase

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.repository.GameRepository
import io.reactivex.Single

class GetGamesUseCase(
    private val schedulers: SchedulerProvider,
    private val gameRepository: GameRepository
) {

    operator fun invoke(players: List<PlayerEntity>): Single<List<PlayerEntity>> {
        val result = gameRepository.fetchGames()
            .map { games ->
                val updatedGames = games.map { game ->
                    game.copy(
                        adversaries = getAdversaries(game, players)
                    )
                }
                players.map { player ->
                    val gamesByPlayer = updatedGames
                        .filter { it.playerId == player.id || it.adversaryId == player.id }
                        .map { game ->
                            game.copy(
                                isPlayerAdversary = game.adversaryId == player.id
                            )
                        }
                    player.copy(
                        games = gamesByPlayer,
                        matches = gamesByPlayer.size.toString(),
                        scores = gamesByPlayer.filter { it.score > it.scoreAdversary }.size.toString()
                    )
                }
            }
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
        return result
    }

    private fun getAdversaries(
        game: GameEntity,
        players: List<PlayerEntity>
    ): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        players
            .filter { it.id != game.playerId }
            .map { hashMap[it.id] = it.name }
        return hashMap
    }
}