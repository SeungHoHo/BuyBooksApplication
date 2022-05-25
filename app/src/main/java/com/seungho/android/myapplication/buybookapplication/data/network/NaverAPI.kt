package com.seungho.android.myapplication.buybookapplication.data.network

import com.seungho.android.myapplication.buybookapplication.data.response.Naver
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverAPI {
    @GET("v1/search/book.json")
    fun getBaseBook(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String,
        @Query("start") start: Int,
        @Query("display") display: Int,
        @Query("sort") sort: String,
    ) : Call<Naver>
}