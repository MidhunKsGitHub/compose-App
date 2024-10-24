package com.midhun.composeapp.Repository

import android.util.Log
import com.google.gson.JsonObject
import com.midhun.composeapp.Config.Config
import com.midhun.composeapp.Model.OtpModel
import com.midhun.composeapp.Network.NetworkResultState
import com.midhun.composeapp.RetrofitApi.RetrofitHelper
import kotlin.math.log

class OtpRepository {

    var result:OtpModel?=null

    suspend fun sendOTP(mobileNo:String,countryCode:String): OtpModel {
        val jsonObject = JsonObject().apply {
            addProperty("mobileNo", mobileNo)
            addProperty("countryCode", countryCode)

        }
        try {
            val response =
                RetrofitHelper.apiService.sendOtp(Config.X_API_KEY, "application/json", jsonObject)
            if (response.isSuccessful) {
                result = response.body()
                Log.d("TAG_OTP", "sendOTPData: $result")
            } else {

                response.errorBody()
                Log.d("TAG_OTP_ERROR", "sendOTPError: ${response.errorBody()}")
            }
        } catch (e: Exception) {

            Log.e("TAG_OTP_EXCEPTION", "sendOTPException: ", e)

        }
        return result!!
    }
}