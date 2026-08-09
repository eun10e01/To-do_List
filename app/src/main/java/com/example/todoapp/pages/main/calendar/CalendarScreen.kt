package com.example.todoapp.pages.main.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.Date
import java.util.Locale

@Composable
fun CalendarDayCell(day: Int?, hasTodo: Boolean, isSelected: Boolean, isSunday: Boolean = false, onDayClick: (Int) -> Unit, modifier: Modifier = Modifier){
    Box(modifier = Modifier
        .aspectRatio(0.7f)
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

@Composable
@Preview
fun CalendarScreen(){
    val currentYearMonth = remember{YearMonth.now()}
    val monthText = remember{
        val formatter = SimpleDateFormat("yyyy년 MM월", Locale.KOREAN)
        formatter.format(Date())
    }
    val totalDays = currentYearMonth.lengthOfMonth() //이번달 실제 총 일수
    //이번달 1일이 시작하는 요일 계산
    val firstDayOfWeek = currentYearMonth.atDay(1).dayOfWeek.value
    val startOffset = if(firstDayOfWeek == 7) 0 else firstDayOfWeek
    var selectedDay by remember{mutableStateOf<Int?>(null)} //선택된 날짜 저장 상태
    val todoDays = remember{setOf(2, 3, 8, 9, 10, 13, 14, 15, 24, 25, 26)} //To-do가 있는 날짜 목록 >> 추후 변경해야 함
    val daysOfWeek = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

    Scaffold(){
        innerPadding -> Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                Text(text = monthText, fontSize = 25.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 25.dp)
                )
            }

            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 5.dp), horizontalArrangement = Arrangement.SpaceAround){
                daysOfWeek.forEach{dayName ->
                    Text(text = dayName, fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if(dayName == "SUN") Color.Red else Color.Black,
                        modifier = Modifier.padding(top = 18.dp)
                    )
                }
            }

            LazyVerticalGrid(columns = GridCells.Fixed(7), modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp)){
                items(startOffset){
                    CalendarDayCell(day = null, hasTodo = false, isSelected = false, onDayClick = {})
                }

                items(totalDays){ index ->
                    val day = index + 1
                    val isSunday = (startOffset + index) % 7 == 0

                    CalendarDayCell(day = day,
                        hasTodo = todoDays.contains(day),
                        isSelected = (selectedDay == day),
                        isSunday = isSunday,
                        onDayClick = {clickedDay -> selectedDay = clickedDay}
                    )
                }
            }
        }
    }
}