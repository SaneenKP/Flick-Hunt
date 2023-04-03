package com.example.flickhunt.retrofit

import com.example.flickhunt.BuildConfig
import com.example.flickhunt.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MovieRetrofitService @Inject constructor() {

    @Singleton
    @Provides
    fun getInterceptor() : Interceptor {
        val requestInterceptor = Interceptor{

            val url = it.request()
                .url
                .newBuilder()
                .addQueryParameter(Constants.API_KEY_QUERY , BuildConfig.API_KEY)
                .build()

            val request = it.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor it.proceed(request)
        }
        return requestInterceptor
    }


    @Singleton
    @Provides
    fun getGsonConverterFactory() : GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    @Singleton
    @Provides
    fun getOkHttpClient() : OkHttpClient {

        /***
         * Only use the logging interceptor to log , Do not use it in production.
         * This can cause a serious security issue and google play might reject it.
         */
        var httLog : HttpLoggingInterceptor = HttpLoggingInterceptor()
        httLog.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(getInterceptor())
            .addInterceptor(httLog)
            .connectTimeout(Constants.connectionTimeOutSeconds , TimeUnit.SECONDS)
            .build()

        return okHttpClient
    }

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(getGsonConverterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun getMoviesApiService() : MoviesApiService{
        return  getRetrofit().create(MoviesApiService::class.java)
    }

}