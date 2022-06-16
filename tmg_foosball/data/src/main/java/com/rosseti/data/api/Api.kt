package com.rosseti.data.api

import com.rosseti.data.model.ScoreModel
import com.rosseti.data.model.ScoreResponse
import io.reactivex.Single
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface Api {

    @GET("/scores")
    fun fetchScores(): Single<List<ScoreModel>>
}