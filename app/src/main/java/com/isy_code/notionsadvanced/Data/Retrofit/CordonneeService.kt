package com.isy_code.notionsadvanced.Data.Retrofit

import com.isy_code.notionsadvanced.Entities.Coordinate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CordonneeService {
    @POST("/")
    suspend fun updateAngles(@Body coordinate: Coordinate)
}