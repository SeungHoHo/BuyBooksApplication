package com.seungho.android.myapplication.buybookapplication.retrofit

import com.seungho.android.myapplication.buybookapplication.data.network.NaverAPI
import com.seungho.android.myapplication.buybookapplication.data.url.Url
import com.seungho.android.myapplication.buybookapplication.data.repository.NaverApiRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val RetrofitModule = module {

    single<NaverAPI> {
        Retrofit.Builder()
            .baseUrl(Url.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NaverAPI::class.java)
    }
    factory { NaverApiRepository(get()) }

}
