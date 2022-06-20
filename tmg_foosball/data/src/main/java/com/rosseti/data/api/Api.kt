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
        @Field("name") name: String
    ): Single<GamerModel>

    @FormUrlEncoded
    @POST(value = "scores")
    fun createScore(
        @Field("name") name: String
    ): Single<GamerModel>

    @GET("/games")
    fun fetchGames(): Single<List<GameModel>>

    @FormUrlEncoded
    @PUT(value = "games/{gameId}")
    fun updateGame(
        @Path("gameId") gameId: String,
        @Field("gamer_id") gamerId: String,
        @Field("adversary") adversary: String,
        @Field("score") score: Int,
        @Field("score_adversary") scoreAdversary: Int
    ): Single<GameModel>

    @FormUrlEncoded
    @POST(value = "games")
    fun createGame(
        @Field("gamer_id") gamerId: String,
        @Field("adversary") adversary: String,
        @Field("score") score: Int,
        @Field("score_adversary") scoreAdversary: Int
    ): Single<GameModel>
}