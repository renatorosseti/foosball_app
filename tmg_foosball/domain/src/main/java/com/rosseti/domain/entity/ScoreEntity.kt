package com.rosseti.domain.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScoreEntity(
    val id: Int,
    val name: String,
    val matches: String,
    val scores: String,
    val results: List<ResultEntity>
): Parcelable

@Parcelize
data class ResultEntity(
    val id: Int,
    val adversary: String,
    val score: String,
    val adversaryScore: String,
    val result: String,
    val isWinner: Boolean
): Parcelable
