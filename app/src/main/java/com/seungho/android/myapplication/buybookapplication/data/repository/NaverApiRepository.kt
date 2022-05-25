package com.seungho.android.myapplication.buybookapplication.data.repository

import com.seungho.android.myapplication.buybookapplication.data.response.Naver
import com.seungho.android.myapplication.buybookapplication.data.network.NaverAPI
import retrofit2.Call

class NaverApiRepository(private val service: NaverAPI) {
    fun getApiList(clientId: String, clientSecret: String, query: String, start: Int, display: Int, sort: String):
            Call<Naver> {
        return service.getBaseBook(clientId, clientSecret, query, start, display, sort)
    }
}