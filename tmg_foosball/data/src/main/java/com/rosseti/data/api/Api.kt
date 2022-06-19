package com.rosseti.data.api

import com.rosseti.data.model.GameModel
import com.rosseti.data.model.GamerModel
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface Api {

    @GET("/scores")
    fun fetchScores(): Single<List<GamerModel>>

    @FormUrlEncoded
    @PUT(value = "scores/{scoreId}")
    fun updateScore(
        @Path("scoreId") scoreId: Int,
        @Field("name") name: String,
        @Field("games") games: List<GameModel>
    ): Single<GamerModel>

    @FormUrlEncoded
    @POST(value = "scores")
    fun createScore(
        @Field("name") name: String,
        @Field("games") games: List<GameModel>
    ): Single<GamerModel>
}