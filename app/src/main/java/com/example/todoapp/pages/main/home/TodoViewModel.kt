package com.example.todoapp.pages.main.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel(){
    var todoList = mutableStateListOf<ToDoItemData>()

    fun addTodo(title: String, time: String?, startDate: String, endDate: String, isRepeat: Boolean, repeatOption: String?){
        val type = when{
            isRepeat -> ToDoType.REPEAT
            startDate != endDate -> ToDoType.RANGE
            else -> ToDoType.NORMAL
        }
        val newItem = ToDoItemData(
            title = title,
            time = time,
            startDate = startDate,
            endDate = endDate,
            type = type,
            repeatOption = if(isRepeat) repeatOption else null,
            order = todoList.size
        )

        todoList.add(newItem)
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
        todoList.forEachIndexed{index, toDoItem -> todoList[index] = toDoItem.copy(order = index)}
    }

    fun updateTodo(id: Long, newTitle: String, newTime: String?, newStartDate: String, newEndDate: String, newIsRepeat: Boolean, newRepeatOption: String?){
        val index = todoList.indexOfFirst {it.id == id}

        if(index != -1){
            val currentItem = todoList[index]
            val newType = when{
                newIsRepeat -> ToDoType.REPEAT
                newStartDate != newEndDate -> ToDoType.RANGE
                else -> ToDoType.NORMAL
            }

            todoList[index] = currentItem.copy(
                title = newTitle,
                time = newTime,
                startDate = newStartDate,
                endDate = newEndDate,
                type = newType,
                repeatOption = if(newIsRepeat) newRepeatOption else null
            )
        }
    }

    fun deleteTodo(id: Long){
        todoList.removeAll{it.id == id}
        todoList.forEachIndexed {index, toDoItem -> todoList[index] = toDoItem.copy(order = index)}
    }
}