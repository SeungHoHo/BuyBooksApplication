package com.seungho.android.myapplication.buybookapplication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seungho.android.myapplication.buybookapplication.data.preference.AppPreferenceManager
import com.seungho.android.myapplication.buybookapplication.screen.home.HomeViewModel
import com.seungho.android.myapplication.buybookapplication.screen.home.detail.BookDetailViewModel
import com.seungho.android.myapplication.buybookapplication.screen.mypage.MyPageViewModel
import com.seungho.android.myapplication.buybookapplication.util.event.MenuChangeEventBus
import com.seungho.android.myapplication.buybookapplication.util.provider.DefaultResourcesProvider
import com.seungho.android.myapplication.buybookapplication.util.provider.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MyPageViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { BookDetailViewModel() }

    single { AppPreferenceManager(androidApplication()) }
    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication()) }

    single { Dispatchers.IO }
    single { Dispatchers.Main }

    single { MenuChangeEventBus() }

    single { Firebase.firestore }
    single { FirebaseAuth.getInstance() }



}