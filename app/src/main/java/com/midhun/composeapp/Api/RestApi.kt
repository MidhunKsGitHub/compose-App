package com.midhun.composeapp.Api


import com.google.gson.JsonObject
import com.midhun.composeapp.Config.Config
import com.midhun.composeapp.Model.Country
import com.midhun.composeapp.Model.Data
import com.midhun.composeapp.Model.OtpModel
import com.midhun.composeapp.Model.Root
import org.json.JSONObject

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RestApi {

    @POST("user/wallet/remittance/countries")
    suspend fun getCountries(
        @Header("x-api-key") apiKey: String,
        @Header("Content-Type") contentType: String,
        @Body jsonObject: JsonObject
    ): Response<Root>

    @POST("user/generate-otp")
    suspend fun sendOtp(
        @Header("x-api-key") apiKey: String,
        @Header("Content-Type") contentType: String,
        @Body jsonObject: JsonObject
    ): Response<OtpModel>
}
