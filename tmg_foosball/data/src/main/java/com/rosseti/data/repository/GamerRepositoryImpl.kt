package com.rosseti.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.rosseti.data.api.Api
import com.rosseti.data.model.GamerModel
import com.rosseti.data.paging.GamerDataSource
import com.rosseti.domain.entity.GameEntity
import com.rosseti.domain.entity.GamerEntity
import com.rosseti.domain.repository.GamerRepository
import io.reactivex.Flowable
import io.reactivex.Single

class GamerRepositoryImpl(private val api: Api) : GamerRepository {
    override fun fetchGamers(): Flowable<PagingData<GamerEntity>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 20
            )
        ) {
            GamerDataSource(api)
        }.flowable

        return pager
    }

    override fun updateGamer(
        scoreId: Int,
        name: String
    ): Single<GamerEntity> =
        api.updateScore(scoreId, name)
            .map {
                createGamerEntity(it)
            }

    override fun createGamer(
        name: String
    ): Single<GamerEntity> =
        api.createScore(name)
            .map {
                createGamerEntity(it)
            }

    private fun createGamerEntity(it: GamerModel) = GamerEntity(
        id = it.id,
        name = it.name
    )
}
