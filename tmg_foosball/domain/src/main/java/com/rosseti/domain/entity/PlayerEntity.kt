package com.rosseti.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerEntity(
    val id: String = "",
    val name: String = "",
    val matches: String = "",
    val scores: String = "",
    val games: List<GameEntity> = listOf()
) : Parcelable

@Parcelize
data class GameEntity(
    val id: String = "",
    val playerId: String = "",
    val adversaryId: String = "",
    val adversary: String = "",
    val playerName: String = "",
    val score: String = "",
    val scoreAdversary: String = "",
    val result: String = "",
    val isWinner: Boolean = score > scoreAdversary,
    val isPlayerAdversary: Boolean = false,
    val adversaries: HashMap<String, String> = HashMap()
) : Parcelable
