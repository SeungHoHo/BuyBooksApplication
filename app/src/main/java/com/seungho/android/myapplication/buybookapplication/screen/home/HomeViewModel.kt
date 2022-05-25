package com.seungho.android.myapplication.buybookapplication.screen.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.seungho.android.myapplication.buybookapplication.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel: BaseViewModel() {

    val bookHomeStateLiveData = MutableLiveData<HomeState>(HomeState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        bookHomeStateLiveData.value = HomeState.Success
    }
}