package com.seungho.android.myapplication.buybookapplication.screen.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.seungho.android.myapplication.buybookapplication.data.preference.AppPreferenceManager
import com.seungho.android.myapplication.buybookapplication.screen.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyPageViewModel(
    private val appPreferenceManager: AppPreferenceManager
) : BaseViewModel() {

    val myPageStateLiveData = MutableLiveData<MyPageState>(MyPageState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        myPageStateLiveData.value = MyPageState.Loading
        appPreferenceManager.getIdToken()?.let {
            myPageStateLiveData.value = MyPageState.Login(it)
        } ?: kotlin.run {
            myPageStateLiveData.value = MyPageState.Success.NotRegistered
        }
    }

    fun saveToken(idToken: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            appPreferenceManager.putIdToken(idToken)
            fetchData()
        }
    }

    fun setUserInfo(firebaseUser: FirebaseUser?) = viewModelScope.launch {
        firebaseUser?.let { user ->
            myPageStateLiveData.value = MyPageState.Success.Registered(
                userName = user.displayName ?: "익명",
                profileImageUri = user.photoUrl
            )
        } ?: kotlin.run {
            myPageStateLiveData.value = MyPageState.Success.NotRegistered
        }
    }

    fun signOut() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            appPreferenceManager.removeIdToken()
        }
        fetchData()
    }
}