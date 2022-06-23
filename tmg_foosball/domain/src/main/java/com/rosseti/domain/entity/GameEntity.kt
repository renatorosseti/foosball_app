package com.rosseti.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameEntity(
    val id: String = "",
    val _playerId: String = "",
    val _adversaryId: String = "",
    val _adversary: String = "",
    val _playerName: String = "",
    val _score: String = "",
    val _scoreAdversary: String = "",
    val isPlayerAdversary: Boolean = false
) : Parcelable {
    val adversary
    get() = if (isPlayerAdversary) _playerName else _adversary

    val playerName
    get() = if (isPlayerAdversary) _adversary else _playerName

    val playerId
    get() = if (isPlayerAdversary) _adversaryId else _playerId

    val adversaryId
    get() = if (isPlayerAdversary) _playerId else _adversaryId

    val score
    get() = if (isPlayerAdversary) _scoreAdversary else _score

    val scoreAdversary
    get() = if (isPlayerAdversary) _score else _scoreAdversary

    val result
    get() = if (isPlayerAdversary) "$_scoreAdversary x $_score" else "$score x $scoreAdversary"

    val isWinner
    get() = score > scoreAdversary
}