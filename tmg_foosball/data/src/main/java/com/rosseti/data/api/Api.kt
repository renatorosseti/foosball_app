package com.rosseti.data.api

import com.rosseti.data.model.GameModel
import com.rosseti.data.model.PlayerModel
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface Api {

    @GET("/scores")
    fun fetchPlayers(): Single<List<PlayerModel>>

    @FormUrlEncoded
    @PUT(value = "scores/{id}")
    fun updatePlayer(
        @Path("id") id: Int,
        @Field("name") name: String
    ): Single<PlayerModel>

    @FormUrlEncoded
    @POST(value = "scores")
    fun createPlayer(
        @Field("name") name: String
    ): Single<PlayerModel>

    @GET("/games")
    fun fetchGames(): Single<List<GameModel>>

    @FormUrlEncoded
    @PUT(value = "games/{id}")
    fun updateGame(
        @Path("id") id: String,
        @Field("gamer_id") gamerId: String,
        @Field("adversary_id") adversaryId: String,
        @Field("adversary") adversary: String,
        @Field("score") score: String,
        @Field("score_adversary") scoreAdversary: String
    ): Single<GameModel>

    @FormUrlEncoded
    @POST(value = "games")
    fun createGame(
        @Field("gamer_id") gamerId: String,
        @Field("adversary_id") adversaryId: String,
        @Field("adversary") adversary: String,
        @Field("score") score: String,
        @Field("score_adversary") scoreAdversary: String
    ): Single<GameModel>
}