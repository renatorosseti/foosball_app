package com.rosseti.data.mapper

import com.rosseti.data.model.ScoreModel
import com.rosseti.data.model.ScoreResponse
import com.rosseti.data.util.BaseMapper
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
        scores = source.scores
    )

    override fun transformTo(source: ScoreModel): ScoreEntity =
        ScoreEntity(
            id = source.id,
            name = source.name,
            matches = source.matches,
            scores = source.scores
        )
}