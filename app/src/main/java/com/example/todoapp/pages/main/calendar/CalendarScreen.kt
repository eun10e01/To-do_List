package com.example.todoapp.pages.main.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.pages.main.home.ScheduleEditScreen
import com.example.todoapp.ui.theme.NanumGothic
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.example.todoapp.pages.main.home.TodoViewModel

@Composable
fun CalendarDayCell(day: Int?, hasTodo: Boolean, isSelected: Boolean, isSunday: Boolean = false, onDayClick: (Int) -> Unit, modifier: Modifier = Modifier){
    Box(modifier = Modifier
        .height(70.dp)
        .border(0.3.dp, Color.Black)
        .clickable(enabled = day != null){
            if(day != null) onDayClick(day)
        }
        .padding(4.dp),
        contentAlignment = Alignment.TopCenter
    ){
        if(day != null){
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ){
                Text(text = day.toString(),
                    fontSize = 12.sp,
                    fontFamily = NanumGothic,
                    fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if(isSunday) Color.Red else Color.Black
                )

                if(hasTodo){
                    Box(modifier = Modifier
                        .width(35.dp)
                        .height(4.dp)
                        .background(color = Color(0xFFBDCEBD), shape = RoundedCornerShape(2.dp))
                    )
                }
            }
        }
    }
}

fun getDaysInMonth(calendar: Calendar): List<Date?>{
    val cal = calendar.clone() as Calendar
    cal.set(Calendar.DAY_OF_MONTH, 1)

    val firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1
    val maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

    val list = mutableListOf<Date?>()

    for(i in 0 until firstDayOfWeek){
        list.add(null)
    }

    for(i in 1..maxDay){
        cal.set(Calendar.DAY_OF_MONTH, i)
        list.add(cal.time)
    }

    return list
}

@Composable
fun ToDoItemCard(title: String, time: String? = null, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier){
    Row(modifier = Modifier.fillMaxWidth()){
        CompositionLocalProvider(LocalRippleConfiguration provides RippleConfiguration(Color(0xFFF2F8E3))){
            Checkbox(checked = isChecked, onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFBDCEBD),
                    uncheckedColor = Color.Black,
                    checkmarkColor = Color.White
                ),
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Text(
            text = title,
            fontSize = 15.sp,
            fontFamily = NanumGothic,
            fontWeight = FontWeight.Medium,
            color = if (isChecked) Color.Gray else Color.Black,
            textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
            modifier = Modifier
                .weight(1f)
                .padding(start = 3.dp, top = 11.dp)
        )

        if (time != null) {
            Text(
                text = time,
                fontSize = 15.sp,
                fontFamily = NanumGothic,
                fontWeight = FontWeight.Medium,
                color = if (isChecked) Color.Gray else Color.Black,
                textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
                modifier = Modifier
                    .padding(top = 11.dp, end = 30.dp)
            )
        }
    }

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        thickness = 0.3.dp,
        color = Color.Black
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(navController: NavHostController, todoViewModel: TodoViewModel = viewModel()){
    var currentCalendar by remember {mutableStateOf(Calendar.getInstance())}

    var selectedDate by remember {mutableStateOf<Date?>(null)}
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var showBottomSheet by remember {mutableStateOf(false)}

    val yearMonthFormat = remember {SimpleDateFormat("yyyy년 MM월", Locale.KOREAN)}
    val fullDateFormat = remember {SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.KOREAN)}
    val navDateFormat = remember {SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN)}
    val dayFormat = remember {SimpleDateFormat("dd일 EEEE", Locale.KOREAN)}

    LaunchedEffect(selectedDate, showBottomSheet){
        if(showBottomSheet && selectedDate != null){
            val dateStr = navDateFormat.format(selectedDate!!)
            todoViewModel.loadTodosForDate(userId = 1L, dateStr = dateStr)
        }
    }

    LaunchedEffect(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH)){
        todoViewModel.loadTodoDatesForMonth(userId = 1L, year = currentCalendar.get(Calendar.YEAR), month = currentCalendar.get(Calendar.MONTH))
    }

    fun moveToPreviousMonth(){
        val newCal = currentCalendar.clone() as Calendar

        newCal.add(Calendar.MONTH, -1)
        currentCalendar = newCal
    }

    fun moveToNextMonth(){
        val newCal = currentCalendar.clone() as Calendar

        newCal.add(Calendar.MONTH, 1)
        currentCalendar = newCal
    }

    Scaffold(){
        innerPadding -> Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                IconButton(onClick = {moveToPreviousMonth()}){
                    Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "이전 달",
                        modifier = Modifier
                            .size(32.dp)
                    )
                }

                Text(text = yearMonthFormat.format(currentCalendar.time), fontSize = 25.sp, fontFamily = NanumGothic, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )

                IconButton(onClick = {moveToNextMonth()}){
                    Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "다음 달",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 5.dp), horizontalArrangement = Arrangement.SpaceAround){
                val daysOfWeek = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

                daysOfWeek.forEach{dayName ->
                    Text(text = dayName, fontSize = 11.sp,
                        fontFamily = NanumGothic,
                        fontWeight = FontWeight.SemiBold,
                        color = if(dayName == "SUN") Color.Red else Color.Black,
                        modifier = Modifier.padding(top = 18.dp)
                    )
                }
            }

            val daysInMonth = remember(currentCalendar) {getDaysInMonth(currentCalendar)}

            LazyVerticalGrid(columns = GridCells.Fixed(7), modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp)){
                items(daysInMonth){date ->
                    if(date != null){
                        val cal = Calendar.getInstance().apply{time = date}
                        val dayNum = cal.get(Calendar.DAY_OF_MONTH)
                        val isSunday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                        val isSelected = selectedDate == date

                        val apiDate = navDateFormat.format(date)
                        val hasTodo = todoViewModel.todoDates.contains(apiDate)

                        CalendarDayCell(day = dayNum, hasTodo = hasTodo, isSelected = isSelected, isSunday = isSunday, onDayClick = {selectedDate = date; showBottomSheet = true})
                    }
                    else{
                        Spacer(modifier = Modifier.height(70.dp))
                    }
                }
            }
        }

        if(showBottomSheet && selectedDate != null){
            ModalBottomSheet(
                onDismissRequest = {showBottomSheet = false},
                sheetState = sheetState,
                containerColor = Color.Transparent,
                dragHandle = null
            ){
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.55f)
                    .padding(vertical = 12.dp)
                    .background(color = Color(0xFFBDCEBD), shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                ){
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)){
                        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(text = dayFormat.format(selectedDate!!), fontSize = 18.sp, fontFamily = NanumGothic, fontWeight = FontWeight.Bold)

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(imageVector = Icons.AutoMirrored.Filled.List,
                                contentDescription = "할일목록수정",
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable(
                                        interactionSource = remember {MutableInteractionSource()},
                                        indication = null
                                    ){
                                        showBottomSheet = false

                                        val dateParam = navDateFormat.format(selectedDate!!)
                                        navController.navigate("edit_schedule/$dateParam")
                                    }
                            )
                        }

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.White, shape = RoundedCornerShape(16.dp))
                            .padding(16.dp)
                        ){
                            if(!todoViewModel.todoList.isEmpty()){
                                LazyColumn(modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ){
                                    items(todoViewModel.todoList, key = {it.id}){item ->
                                        ToDoItemCard(
                                            title = item.title,
                                            time = item.time,
                                            isChecked = item.isChecked,
                                            onCheckedChange = {todoViewModel.toggleCheck(item.id)}
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview(){
    val navController = rememberNavController()
    CalendarScreen(navController = navController)
}