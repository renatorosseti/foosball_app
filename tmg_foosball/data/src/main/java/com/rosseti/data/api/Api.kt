package com.rosseti.data.api

import com.rosseti.data.model.ScoreModel
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface Api {

    @GET("/scores")
    fun fetchScores(): Single<List<ScoreModel>>

    @FormUrlEncoded
    @PUT(value = "scores/{scoreId}")
    fun updateScore(
        @Path("scoreId") scoreId: Int,
        @Field("name") name: String,
        @Field("matches") matches: String,
        @Field("scores") scores: String
    ): Single<ScoreModel>

    @FormUrlEncoded
    @POST(value = "scores")
    fun createScore(
        @Field("name") name: String,
        @Field("matches") matches: String,
        @Field("scores") scores: String
    ): Single<ScoreModel>
}