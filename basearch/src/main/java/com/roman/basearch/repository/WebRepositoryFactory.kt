package com.roman.basearch.repository

import com.roman.basearch.viewmodel.getKoinInstance
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

class WebRepositoryFactory {

    fun <T> create(repository: WebRepository<T>): T {

        val okHttpClient = getKoinInstance<OkHttpClient>()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(repository.context.getString(repository.baseUrlResource))
            .build()

        return retrofit.create(repository.repositoryClass)
    }

}
