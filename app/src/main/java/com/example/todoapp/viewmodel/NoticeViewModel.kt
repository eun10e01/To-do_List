package com.example.todoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.dto.NoticeResponse
import com.example.todoapp.repository.NoticeRepository
import kotlinx.coroutines.launch

class NoticeViewModel : ViewModel() {

    private val repository = NoticeRepository()

    var notices by mutableStateOf<List<NoticeResponse>>(emptyList())
        private set

    var noticeErrorMessage by mutableStateOf("")
        private set

    fun loadNotices() {
        viewModelScope.launch {

            val response = repository.getNotices()

            if (response.isSuccessful) {

                notices = response.body() ?: emptyList()

            } else {

                noticeErrorMessage = "공지사항을 불러오지 못했습니다"
            }
        }
    }
}