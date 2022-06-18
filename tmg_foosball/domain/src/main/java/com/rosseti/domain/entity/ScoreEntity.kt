package com.rosseti.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScoreEntity(
    val id: Int,
    val name: String,
    val matches: String,
    val scores: String
): Parcelable
