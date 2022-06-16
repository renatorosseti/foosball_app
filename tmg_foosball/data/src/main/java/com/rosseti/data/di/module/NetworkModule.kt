package com.rosseti.data.di.module

import androidx.annotation.VisibleForTesting
import com.rosseti.data.api.Api
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Named("retrofitDefault")
    fun provideRetrofit(rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
                        baseUrl: String,okHttpClient: OkHttpClient, moshiConverterFactory: MoshiConverterFactory): Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    fun provideAudioBookServiceApi(@Named("retrofitDefault") retrofit: Retrofit): Api
       = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    @VisibleForTesting
    fun provideOkHttpClient() : OkHttpClient {

        val builder = OkHttpClient.Builder()

        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)

        builder.addInterceptor(RequestInterceptor())

        return builder.build()
    }

    @Provides
    @Singleton
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun provideRxJava2CallAdapter(): RxJava2CallAdapterFactory
       = RxJava2CallAdapterFactory.create()

    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory
       = MoshiConverterFactory.create(moshi)

    @Provides
    fun provideMoshi(): Moshi
     = Moshi.Builder()
        .add(Wrapped.ADAPTER_FACTORY)
        .add(KotlinJsonAdapterFactory())
        .build()


    @Provides
    @Singleton
    fun getUrl():String{
        return  "https://62717751c455a64564b38687.mockapi.io/api/"
    }

}

class RequestInterceptor : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        //add api key here to every request
        val url = originalUrl.newBuilder()
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}