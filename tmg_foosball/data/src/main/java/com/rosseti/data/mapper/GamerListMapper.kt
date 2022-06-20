package com.rosseti.data.mapper

import com.rosseti.data.model.GamerModel
import com.rosseti.data.model.GamerResponse
import com.rosseti.data.util.BaseMapper
import com.rosseti.domain.entity.GamerEntity
import com.rosseti.domain.entity.GamerListEntity

object GamerListToDomainMapper : BaseMapper<GamerListEntity, List<GamerModel>>() {

    override fun transformFrom(source: List<GamerModel>): GamerListEntity {

        return GamerListEntity(gamerList = GamerModelToDomainMapper.transformToList(source))
    }

    override fun transformTo(source: GamerListEntity): GamerResponse {
        val scoreResponse = GamerResponse()
        scoreResponse.addAll(source.gamerList.let {
            GamerModelToDomainMapper.transformFromList(
                it
            )
        })
        return scoreResponse
    }
}

object GamerModelToDomainMapper : BaseMapper<GamerModel, GamerEntity>() {

    override fun transformFrom(source: GamerEntity): GamerModel = GamerModel(
        id = source.id,
        name = source.name
    )

    override fun transformTo(source: GamerModel): GamerEntity =
        createGamerEntity(source)

    private fun createGamerEntity(it: GamerModel) = GamerEntity(
        id = it.id,
        name = it.name
    )
}