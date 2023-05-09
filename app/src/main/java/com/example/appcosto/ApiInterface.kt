package com.example.appcosto

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("tips")
    fun getData(): Call<List<MyDataItem>>
}