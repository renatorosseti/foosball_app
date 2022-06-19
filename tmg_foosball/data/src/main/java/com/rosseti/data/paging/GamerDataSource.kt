package com.rosseti.data.paging

import androidx.paging.rxjava2.RxPagingSource
import com.rosseti.data.api.Api
import com.rosseti.data.mapper.ScoreListToDomainMapper
import com.rosseti.domain.entity.GamerEntity
import com.rosseti.domain.entity.GamerListEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

class GamerDataSource(val api: Api) : RxPagingSource<Int, GamerEntity>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, GamerEntity>> {
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

    private fun toLoadResult(data: GamerListEntity, position: Int): LoadResult<Int, GamerEntity> {

        return LoadResult.Page(
            data = data.gamerList,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.totalPages) null else position + 1
        )
    }
}