package com.seungho.android.myapplication.buybookapplication.screen.home

sealed class HomeState {

    object Uninitialized: HomeState()

    object Success: HomeState()
}