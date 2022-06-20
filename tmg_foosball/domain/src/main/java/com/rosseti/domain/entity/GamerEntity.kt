package com.rosseti.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GamerEntity(
    val id: String = "",
    val name: String = "",
    val matches: Int = 0,
    val scores: Int = 0,
    val games: List<GameEntity> = listOf()
) : Parcelable

@Parcelize
data class GameEntity(
    val id: String = "",
    val adversary: String = "",
    val score: Int = 0,
    val scoreAdversary: Int = 0,
    val result: String = "",
    val isWinner: Boolean = score > scoreAdversary
) : Parcelable
