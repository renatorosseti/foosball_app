package com.rosseti.domain.repository

import androidx.paging.PagingData
import com.rosseti.domain.entity.GamerEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface GamerRepository {
    fun fetchGamers(): Flowable<PagingData<GamerEntity>>
    fun updateGamer(id: Int, name: String): Single<GamerEntity>
    fun createGamer(name: String): Single<GamerEntity>
}