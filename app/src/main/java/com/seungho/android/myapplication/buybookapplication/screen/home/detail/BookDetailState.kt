package com.seungho.android.myapplication.buybookapplication.screen.home.detail

sealed class BookDetailState {

    object Uninitialized: BookDetailState()

    object Loading: BookDetailState()

    object Success: BookDetailState()

}