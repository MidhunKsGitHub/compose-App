package com.midhun.composeapp.Repository


import com.google.gson.JsonObject
import com.midhun.composeapp.Api.RestApi
import com.midhun.composeapp.Config.Config
import com.midhun.composeapp.Model.Country
import com.midhun.composeapp.RetrofitApi.RetrofitHelper


class CountryRepository() {

    private val countryService = RetrofitHelper.apiService

    suspend fun getCountry(): ArrayList<Country>? {
        var countryRespone = ArrayList<Country>()
        val jsonObject = JsonObject()
        jsonObject.addProperty("providerId", "2")

        val result = countryService.getCountries(Config.X_API_KEY, "application/json", jsonObject)
        if (result.isSuccessful) {
            countryRespone = result.body()!!.data!!.countries!!
        }
        return countryRespone
    }
    companion object {
        @Volatile
        private var instance: CountryRepository? = null

        fun getInstance(apiService: RestApi): CountryRepository =
            instance ?: synchronized(this) {
                instance ?: CountryRepository()
            }.also { instance = it }
    }
}