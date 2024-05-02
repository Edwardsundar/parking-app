package com.app.parker.data

import com.app.parker.repository.ParkingPlace
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Objects

interface ParkerApi {

    @GET("signin")
    suspend fun singIn(
        @Query("name") name: String,
        @Query("password") password: String
    ): Boolean

    @GET("signup")
    suspend fun singUp(
        @Query("name") name: String,
        @Query("password") password: String
    ): Boolean

    @GET("booknewslot")
    suspend fun bookNewSlot(
        @Query("place") place : String,
        @Query("time") time : String,
        @Query("type") type : String,
        @Query("userName") name: String
    ): Boolean

    @GET("usercanenter")
    suspend fun userCanEnter(
        @Query("place") place : String,
        @Query("userName") name: String,
        @Query("type") type: String
    ): Boolean

    @GET("usercanenter")
    suspend fun userCanExit(
        @Query("place") place : String,
        @Query("userName") name: String,
        @Query("type") type: String
    ): String

    @GET("usercanexit")
    suspend fun userCanExit(
        @Query("userName") name: String,
        @Query("place") place : String
    ): String

    @GET("usertime")
    suspend fun userTime(
        @Query("userName") name: String,
        @Query("place") place : String
    ): String

    @GET("getparkingareajson")
    suspend fun getParkingAreaJson(
        @Query("place") place : String,
        @Query("name") name : String,
        @Query("time") time : String,
    ) : ParkingPlace


}