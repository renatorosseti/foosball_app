package com.rosseti.domain.usecase

import com.rosseti.domain.SchedulerProvider
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
                        adversaries = getAdversaries(
                            players,
                            game.playerId,
                            game.adversaryId
                        )
                    )
                }
                players.map { player ->
                    val gamesByPlayer = updatedGames
                        .filter { it.playerId == player.id || it.adversaryId == player.id }
                        .map { game ->
                            game.copy(
                                isPlayerAdversary = game.adversaryId == player.id,
                                result = if (game.adversaryId == player.id) "${game.scoreAdversary} x ${game.score}" else "${game.score} x ${game.scoreAdversary}",
                                isWinner = if (game.adversaryId == player.id) game.scoreAdversary > game.score else game.score > game.scoreAdversary
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
        players: List<PlayerEntity>,
        playerId: String,
        adversaryId: String
    ): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        players
            .filter { it.id != playerId || it.id != adversaryId }
            .map { hashMap[it.id] = it.name }
        return hashMap
    }
}