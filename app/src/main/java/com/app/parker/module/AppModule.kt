package com.app.parker.module

import com.app.parker.constant.Constants
import com.app.parker.data.ParkerApi
import com.app.parker.repository.ParkerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDictionaryApi():ParkerApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ParkerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepo(api : ParkerApi):ParkerRepository {
        return ParkerRepository(api)
    }

}