package com.rosseti.data.mapper

import com.rosseti.data.model.GamerModel
import com.rosseti.data.model.GamerResponse
import com.rosseti.data.util.BaseMapper
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.entity.GamerListEntity

object GamerListToDomainMapper : BaseMapper<GamerListEntity, List<GamerModel>>() {

    override fun transformFrom(source: List<GamerModel>): GamerListEntity {

        return GamerListEntity(playerList = GamerModelToDomainMapper.transformToList(source))
    }

    override fun transformTo(source: GamerListEntity): GamerResponse {
        val scoreResponse = GamerResponse()
        scoreResponse.addAll(source.playerList.let {
            GamerModelToDomainMapper.transformFromList(
                it
            )
        })
        return scoreResponse
    }
}

object GamerModelToDomainMapper : BaseMapper<GamerModel, PlayerEntity>() {

    override fun transformFrom(source: PlayerEntity): GamerModel = GamerModel(
        id = source.id,
        name = source.name
    )

    override fun transformTo(source: GamerModel): PlayerEntity =
        createGamerEntity(source)

    private fun createGamerEntity(it: GamerModel) = PlayerEntity(
        id = it.id,
        name = it.name
    )
}