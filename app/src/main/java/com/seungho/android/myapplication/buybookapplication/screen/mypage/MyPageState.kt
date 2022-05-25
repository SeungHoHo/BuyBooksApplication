package com.seungho.android.myapplication.buybookapplication.screen.mypage

import android.net.Uri
import androidx.annotation.StringRes

sealed class MyPageState {

    object Uninitialized: MyPageState() //초기화 되지 않을때

    object Loading: MyPageState() //로딩중

    data class Login(
        val idToken: String
    ): MyPageState()

    sealed class Success: MyPageState() { //성공

        data class Registered( //로그인 상태
            val userName: String,
            val profileImageUri: Uri?
        ) : Success()

        object NotRegistered: Success() //비로그인 상태

    }

    data class Error( //에러
        @StringRes val messageId: Int,
        val e: Throwable
    ): MyPageState()

}