package com.rosseti.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GamerEntity(
    val id: Int,
    val name: String,
    val matches: String,
    val scores: String,
    val games: MutableList<GameEntity>
): Parcelable

@Parcelize
data class GameEntity(
    val id: Int,
    val adversary: String,
    val score: String,
    val scoreAdversary: String,
    val result: String,
    val isWinner: Boolean
): Parcelable
