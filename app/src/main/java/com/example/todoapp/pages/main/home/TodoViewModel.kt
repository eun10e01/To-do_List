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

    fun moveItem(fromIndex: Int, toIndex: Int){
        if(fromIndex == toIndex || fromIndex !in todoList.indices || toIndex !in todoList.indices){
            return
        }

        val item = todoList.removeAt(fromIndex)

        todoList.add(toIndex, item)
    }

    fun updateTodo(id: Long, newTitle: String, newTime: String?){
        val index = todoList.indexOfFirst {it.id == id}

        if(index != -1){
            val currentItem = todoList[index]
            todoList[index] = currentItem.copy(title = newTitle, time = newTime)
        }
    }

    fun deleteTodo(id: Long){
        todoList.removeAll{it.id == id}
    }
}