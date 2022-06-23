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
