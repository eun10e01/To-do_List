package com.example.todoapp.pages.main.home

import android.icu.util.Calendar
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.dto.TodoCreateRequest
import com.example.todoapp.dto.TodoUpdateRequest
import com.example.todoapp.network.RetrofitClient
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TodoViewModel : ViewModel(){
    private val apiService = RetrofitClient.todoApiService

    private val _todoList = mutableStateListOf<ToDoItemData>()
    val todoList: List<ToDoItemData> get() = _todoList

    private val _todoDates = mutableStateListOf<String>()
    val todoDates: List<String> get() = _todoDates

    var currentSelectedDate: String = SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN).format(Date())

    fun loadTodosForDate(userId: Long = 1L, dateStr: String){
        currentSelectedDate = dateStr
        viewModelScope.launch{
            try{
                val formattedDate = convertToDbDateFormat(dateStr)
                val result = apiService.getTodosByDate(userId, formattedDate)
                _todoList.clear()
                _todoList.addAll(result)
            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun loadTodoDatesForMonth(userId: Long = 1L, year: Int, month: Int){
        viewModelScope.launch{
            try{
                _todoDates.clear()

                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, 1)

                val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)

                for(day in 1..maxDay){
                    calendar.set(Calendar.DAY_OF_MONTH, day)

                    val dateString = dateFormat.format(calendar.time)
                    val todos = apiService.getTodosByDate(userId, dateString)

                    if(todos.isNotEmpty()){
                        _todoDates.add(dateString)
                    }
                }
            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun addTodo(title: String, time: String?, startDate: String, endDate: String, isRepeat: Boolean, repeatOption: String?) {
        val type = when {
            isRepeat -> ToDoType.REPEAT
            startDate != endDate -> ToDoType.RANGE
            else -> ToDoType.NORMAL
        }

        val request = TodoCreateRequest(
            userId = 1L,
            title = title,
            time = time,
            startDate = convertToDbDateFormat(startDate),
            endDate = convertToDbDateFormat(endDate),
            type = type.name,
            isRepeat = isRepeat,
            recurrenceType = if (isRepeat) repeatOption else null,
            recurrenceEndDate = convertToDbDateFormat(endDate)
        )

        viewModelScope.launch{
            try{
                val newItem = apiService.createTodo(request)
                loadTodosForDate(1L, currentSelectedDate)
            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun toggleCheck(id: Long){
        val index = _todoList.indexOfFirst{it.id == id}

        if(index != -1){
            val currentItem = _todoList[index]
            _todoList[index] = currentItem.copy(isChecked = !currentItem.isChecked)

            viewModelScope.launch{
                try{
                    apiService.toggleCheck(id)
                }
                catch (e: Exception){
                    _todoList[index] = currentItem
                }
            }
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

        val item = _todoList.removeAt(fromIndex)

        _todoList.add(toIndex, item)
        _todoList.forEachIndexed{index, toDoItem -> _todoList[index] = toDoItem.copy(order = index)}
    }

    fun updateTodo(id: Long, newTitle: String, newTime: String?, newStartDate: String, newEndDate: String, newIsRepeat: Boolean, newRepeatOption: String?){
        val type = when{
            newIsRepeat -> ToDoType.REPEAT
            newStartDate != newEndDate -> ToDoType.RANGE
            else -> ToDoType.NORMAL
        }

        val request = TodoUpdateRequest(
            title = newTitle,
            time = newTime,
            startDate = convertToDbDateFormat(newStartDate),
            endDate = convertToDbDateFormat(newEndDate),
            type = type.name,
            isRepeat = newIsRepeat,
            recurrenceType = if(newIsRepeat) (newRepeatOption ?: "DAILY") else null,
            recurrenceEndDate = convertToDbDateFormat(newEndDate)
        )

        viewModelScope.launch{
            try{
                apiService.updateTodo(id, request)
                loadTodosForDate(1L, currentSelectedDate)
            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun deleteTodo(id: Long){
        viewModelScope.launch{
            try{
                apiService.deleteTodo(id)
                _todoList.removeAll{it.id == id}
            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun convertToDbDateFormat(dateStr: String): String{
        return try{
            val fromFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN)
            val toFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
            val date = fromFormat.parse(dateStr)
            toFormat.format(date ?: Date())
        }
        catch(e: Exception){
            dateStr
        }
    }
}