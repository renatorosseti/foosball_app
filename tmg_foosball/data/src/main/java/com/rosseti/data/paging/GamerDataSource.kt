package com.rosseti.data.paging

import androidx.paging.rxjava2.RxPagingSource
import com.rosseti.data.api.Api
import com.rosseti.data.mapper.GamerListToDomainMapper
import com.rosseti.domain.entity.PlayerEntity
import com.rosseti.domain.entity.GamerListEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

class GamerDataSource(val api: Api) : RxPagingSource<Int, PlayerEntity>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, PlayerEntity>> {
        val page = params.key ?: 1
        return try {
            val response = api.fetchPlayers()
                .subscribeOn(Schedulers.io())
            response.map {
                val mapResponse = GamerListToDomainMapper.transformFrom(it)
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

    private fun toLoadResult(data: GamerListEntity, position: Int): LoadResult<Int, PlayerEntity> {

        return LoadResult.Page(
            data = data.playerList,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.totalPages) null else position + 1
        )
    }
}