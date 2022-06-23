package com.rosseti.domain.usecase

import com.rosseti.domain.SchedulerProvider
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.extensions.getAdversaries
import com.rosseti.domain.repository.GameRepository
import io.reactivex.Single

class GetGamesUseCase(
    private val schedulers: SchedulerProvider,
    private val gameRepository: GameRepository
) {

    operator fun invoke(players: List<PlayerEntity>): Single<List<PlayerEntity>> {
        val result = gameRepository.fetchGames()
            .map { games ->
                players.map { player ->
                    val gamesByPlayer = games
                        .filter { it.playerId == player.id || it.adversaryId == player.id }
                        .map { game ->
                            game.copy(isPlayerAdversary = game.adversaryId == player.id)
                        }
                    player.copy(
                        games = gamesByPlayer,
                        matches = gamesByPlayer.size.toString(),
                        scores = gamesByPlayer.filter { it.score > it.scoreAdversary }.size.toString(),
                        adversaries = player.getAdversaries(players)
                    )
                }.sortedByDescending { it.matches.toInt() + it.scores.toInt() }
            }
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
        return result
    }
}