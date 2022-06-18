package com.rosseti.data.paging

import androidx.paging.rxjava2.RxPagingSource
import com.rosseti.data.api.Api
import com.rosseti.data.mapper.ScoreListToDomainMapper
import com.rosseti.domain.entity.ScoreEntity
import com.rosseti.domain.entity.ScoreListEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

class ScoreDataSource(val api: Api) : RxPagingSource<Int, ScoreEntity>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ScoreEntity>> {
        val page = params.key ?: 1
        return try {
            val response = api.fetchScores()
                .subscribeOn(Schedulers.io())
            response.map {
                val mapResponse = ScoreListToDomainMapper.transformFrom(it)
                toLoadResult(mapResponse, page)
            }.onErrorReturn {
                LoadResult.Error(it)
            }

        } catch (exception: IOException) {
            Single.just(LoadResult.Error(exception))
        } catch (exception: HttpException) {
            Single.just(LoadResult.Error(exception))
        } catch (exception: InvalidObjectException) {
            Single.just(LoadResult.Error(exception))
        } catch (exception: Exception) {
            Single.just(LoadResult.Error(exception))
        }

    }

    private fun toLoadResult(data: ScoreListEntity, position: Int): LoadResult<Int, ScoreEntity> {

        return LoadResult.Page(
            data = data.scoreList,
            prevKey = if (position == 1) null else position,
            nextKey = if (position == data.totalPages) null else position
        )
    }
}