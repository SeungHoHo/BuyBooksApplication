package com.seungho.android.myapplication.buybookapplication

import android.app.Application
import android.content.Context
import com.seungho.android.myapplication.buybookapplication.retrofit.RetrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BorrowBookApplication: Application() {
    companion object {
        var appContext: Context? = null
            private set
    }
    override fun onCreate() {
        super.onCreate()
        appContext = this

        startKoin {

            androidContext(this@BorrowBookApplication)
            modules(listOf(
                appModule
                , RetrofitModule
            ))
//            modules(networkManager("https://openapi.naver.com/"))
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        appContext = null
    }
}