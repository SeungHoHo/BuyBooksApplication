package com.seungho.android.myapplication.buybookapplication.screen.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.seungho.android.myapplication.buybookapplication.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BookDetailViewModel: BaseViewModel() {

    val bookDetailStateLiveData = MutableLiveData<BookDetailState>(BookDetailState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        bookDetailStateLiveData.value = BookDetailState.Success
    }
}