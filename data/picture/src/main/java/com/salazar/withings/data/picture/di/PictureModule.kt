package com.salazar.withings.data.picture.di

import com.salazar.withings.data.picture.api.PixabayApi
import com.salazar.withings.data.picture.interceptor.ApiKeyInterceptor
import com.salazar.withings.data.picture.repository.PictureRepository
import com.salazar.withings.data.picture.repository.PictureRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object PictureModule {

    @Provides
    fun providePterodactylApi(
        apiKeyInterceptor: ApiKeyInterceptor,
    ): PixabayApi {
        val moshi = Moshi.Builder().build()

        val client = OkHttpClient.Builder().build()

        val okHttpClient: OkHttpClient = client.newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(apiKeyInterceptor)
            .build()

        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl(PixabayApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

        return retrofit.create(PixabayApi::class.java)
    }

    @Provides
    fun providePictureRepository(
        impl: PictureRepositoryImpl,
    ): PictureRepository {
        return impl
    }
}