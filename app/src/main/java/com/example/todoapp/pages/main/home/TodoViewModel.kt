package com.example.todoapp.pages.main.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel(){
    var todoList = mutableStateListOf<ToDoItemData>()

    fun addTodo(title: String, time: String?){
        todoList.add(ToDoItemData(title = title, time = time))
    }
}