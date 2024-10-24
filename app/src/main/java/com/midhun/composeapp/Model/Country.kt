package com.midhun.composeapp.Model

import com.google.gson.annotations.SerializedName


class Country {
    var name: String? = null
    var alpha2Code: String? = null
    var alpha3Code: String? = null
    var currencyName: String? = null
    var currencyCode: String? = null
    var telephoneCode = 0
    var numericCode = 0
    var currencySymbol: String? = null
}


class Data {

    @SerializedName("countries")
    var countries: ArrayList<Country>? = null
}


class Root {
    var success = false
    var statusCode = 0
    var message: String? = null
    var data: Data? = null
}