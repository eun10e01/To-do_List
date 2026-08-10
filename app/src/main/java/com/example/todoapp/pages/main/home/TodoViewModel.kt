package com.example.todoapp.pages.main.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel(){
    var todoList = mutableStateListOf<ToDoItemData>()

    fun addTodo(title: String, time: String?){
        todoList.add(ToDoItemData(title = title, time = time))
    }

    fun toggleCheck(id: Long){
        val index = todoList.indexOfFirst {it.id == id}

        if(index != -1){
            val currentItem = todoList[index]
            todoList[index] = currentItem.copy(isChecked = !currentItem.isChecked)
        }
    }

    fun getProgress(): Float{
        if(todoList.isEmpty()){
            return 0f
        }

        val completedCount = todoList.count{it.isChecked}

        return completedCount.toFloat() / todoList.size
    }
}