package com.rosseti.data.mapper

import com.rosseti.data.model.ResultModel
import com.rosseti.data.model.ScoreModel
import com.rosseti.data.model.ScoreResponse
import com.rosseti.data.util.BaseMapper
import com.rosseti.domain.entity.ResultEntity
import com.rosseti.domain.entity.ScoreEntity
import com.rosseti.domain.entity.ScoreListEntity

object ScoreListToDomainMapper : BaseMapper<ScoreListEntity, List<ScoreModel>>() {

    override fun transformFrom(source: List<ScoreModel>): ScoreListEntity {

        return ScoreListEntity(scoreList = ScoreModelToDomainMapper.transformToList(source))
    }

    override fun transformTo(source: ScoreListEntity): ScoreResponse {
        val scoreResponse = ScoreResponse()
        scoreResponse.addAll(source.scoreList.let {
            ScoreModelToDomainMapper.transformFromList(
                it
            )
        })
        return scoreResponse
    }
}

object ScoreModelToDomainMapper : BaseMapper<ScoreModel, ScoreEntity>() {

    override fun transformFrom(source: ScoreEntity): ScoreModel = ScoreModel(
        id = source.id,
        name = source.name,
        matches = source.matches,
        scores = source.scores,
        results = source.results.map { ResultModel(it.id, it.adversary, it.score, it.adversaryScore) }
    )

    override fun transformTo(source: ScoreModel): ScoreEntity =
        createScoreEntity(source)

    private fun createScoreEntity(it: ScoreModel) = ScoreEntity(
        id = it.id,
        name = it.name,
        matches = it.results.size.toString(),
        scores = it.results.filter { it.score > it.adversaryScore }.size.toString(),
        results = it.results.map { res ->
            ResultEntity(
                id = res.id,
                adversary = res.adversary,
                score = res.score,
                adversaryScore = res.adversaryScore,
                result = "${res.score} x ${res.adversaryScore}",
                isWinner = res.score > res.adversaryScore
            )
        })
}