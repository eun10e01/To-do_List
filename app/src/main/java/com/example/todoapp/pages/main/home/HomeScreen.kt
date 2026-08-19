package com.example.todoapp.pages.main.home

import android.util.Log
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.UserSession
import com.example.todoapp.pages.main.calendar.getDaysInMonth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import com.example.todoapp.pages.main.home.ToDoItemData
import com.example.todoapp.pages.main.home.ScheduleEditScreen
import com.example.todoapp.preferences.UserPreferences
import com.example.todoapp.ui.theme.NanumGothic
import com.example.todoapp.viewmodel.MyPageViewModel
import java.util.Calendar

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

@Composable
fun CustomProgressBar(progress: Float, modifier: Modifier = Modifier){
    val totalBarHeight = 35.dp
    val barShape = RoundedCornerShape(15.dp)

    val trackColor = Color(0xFFDDDDDD)
    val progressColor = Color(0xFFBDCEBD)

    val safeProgress = progress.coerceIn(0f, 1f)
    val percentage = (safeProgress * 100).toInt()

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
        Box(modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 9.dp)
            .fillMaxWidth()
            .height(totalBarHeight)
            .clip(barShape)
            .background(trackColor),
            contentAlignment = Alignment.CenterStart
        ){
            Box(modifier = Modifier
                .fillMaxWidth(safeProgress)
                .fillMaxHeight()
                .clip(barShape)
                .background(progressColor)
            ){
                if(safeProgress >= 0.5f){
                    Text(text = "$percentage%",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = NanumGothic,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp)
                    )
                }
            }

            if(safeProgress < 0.5f){
                Row(modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Spacer(modifier = Modifier.fillMaxWidth(safeProgress))

                    Text(text = "$percentage%",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = NanumGothic,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "0%", fontSize = 10.sp, fontFamily = NanumGothic, color = Color.Black, modifier = Modifier.padding(horizontal = 8.dp))
            Text(text = "100%", fontSize = 10.sp, fontFamily = NanumGothic, color = Color.Black, modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}

@Composable
fun MiniCalendar(todoViewModel: TodoViewModel = viewModel(), modifier: Modifier = Modifier){
    val currentCalendar = remember {Calendar.getInstance()}
    val year = currentCalendar.get(Calendar.YEAR)
    val month = currentCalendar.get(Calendar.MONTH)
    val monthTitle = "${month + 1}월"

    val apiDateFormat = remember {SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)}
/*
    LaunchedEffect(year, month){
        todoViewModel.loadCompletedDatesForMonth(year = year, month = month)
    }
*/
    val daysInMonth = remember(currentCalendar) {getDaysInMonth(currentCalendar)}
    val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")

    Box(modifier = modifier
        .fillMaxHeight()
        .background(Color.White, shape = RoundedCornerShape(7.dp))
        .border(1.dp, Color(0xFFA0A0A0), shape = RoundedCornerShape(7.dp))
        .padding(10.dp)
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
            Text(text = monthTitle, fontSize = 18.sp, fontFamily = NanumGothic, fontWeight = FontWeight.Bold,
                color = Color.Black, modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                daysOfWeek.forEachIndexed{index, day ->
                    Text(text = day, fontSize = 11.sp, fontFamily = NanumGothic, fontWeight = FontWeight.Medium,
                        color = if(index == 0) Color.Red else Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = false
            ){
                items(daysInMonth){date ->
                    if(date != null){
                        val cal = Calendar.getInstance().apply{time = date}
                        val dayNum = cal.get(Calendar.DAY_OF_MONTH)
                        val isSunday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY

                        val dateStr = apiDateFormat.format(date)
                        val isAllCompleted = todoViewModel.completedDates.contains(dateStr)

                        Box(modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxSize(),
                            //.padding(1.dp),
                            contentAlignment = Alignment.Center
                        ){
                            if(isAllCompleted){
                                Box(modifier = Modifier.size(20.dp).border(width = 1.2.dp, color = Color(0xFFD98880), shape = CircleShape))
                            }

                            Text(text = dayNum.toString(), fontSize = 11.sp, fontFamily = NanumGothic, fontWeight = FontWeight.Normal,
                                color = if(isSunday) Color.Red else Color.Black
                            )
                        }
                    }
                    else{
                        Spacer(modifier = Modifier.aspectRatio(1f))
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun HomeScreen(onIconClick: () -> Unit = {}, viewModel: TodoViewModel = viewModel(), myPageViewModel: MyPageViewModel = viewModel()) {
    var achieveDays by remember{mutableIntStateOf(0)}
    val todayText = remember{
        val formatter = SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.KOREAN)
        formatter.format(Date())
    }
    var todoList = viewModel.todoList
    val progress = viewModel.getProgress()

    val currentCalendar = remember{Calendar.getInstance()}
    val year = currentCalendar.get(Calendar.YEAR)
    val month = currentCalendar.get(Calendar.MONTH)

    val todayStr = remember {SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN).format(Date())}

    val context = LocalContext.current
    val userPreferences = remember {UserPreferences(context)}

    LaunchedEffect(Unit){
        val userId = userPreferences.getUserId()

        Log.d("LOGIN_TEST", "저장된 userId = $userId")

        if (userId != -1L) {
            UserSession.currentUserId = userId
            myPageViewModel.loadUser(userId)

            viewModel.loadTodosForDate(dateStr = todayStr)
            viewModel.loadCompletedDatesForMonth(year = year, month = month + 1)
        }
    }

    DisposableEffect(Unit){
        val userId = UserSession.currentUserId

        if(userId != null && userId != -1L){
            viewModel.loadCompletedDatesForMonth(year = year, month = month + 1)
        }

        onDispose {  }
    }

    val userNickname = myPageViewModel.user?.nickname ?: "김이독"

    Scaffold(){
        innerPadding -> Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ){

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "${userNickname}님", fontSize = 25.sp, fontFamily = NanumGothic, fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 20.dp, top = 35.dp, bottom = 15.dp)
                    )

                    Box(modifier = Modifier
                        .padding(start = 20.dp, end = 13.dp)
                        .height(110.dp)
                        .border(width = 0.3.dp, Color(0xFF444F34), shape = RoundedCornerShape(7.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(7.dp))
                    ){
                        Column(modifier = Modifier.fillMaxSize()){
                            Text(text = "오늘 달성률", fontSize = 18.sp, fontFamily = NanumGothic, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 12.dp, top = 10.dp)
                            )

                            CustomProgressBar(
                                progress = progress,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }

                    Box(modifier = Modifier
                        .padding(start = 20.dp, top = 16.dp, end = 13.dp)
                        .fillMaxWidth()
                        .height(55.dp)
                        .border(width = 1.dp, Color(0xFFEDF5E2), shape = RoundedCornerShape(7.dp))
                        .background(color = Color(0xFFF2F8E3), shape = RoundedCornerShape(7.dp))
                    ){
                        Row(modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Text(text = "${achieveDays}일 ", fontSize = 15.sp, fontFamily = NanumGothic, fontWeight = FontWeight.Bold,
                                style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                                modifier = Modifier
                            )

                            Text(text = "연속 To-Do 달성!", fontSize = 15.sp, fontFamily = NanumGothic,
                                style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                                modifier = Modifier
                            )
                        }
                    }
                }

                Box(modifier = Modifier
                    .weight(1f)
                    .padding(top = 35.dp, end = 20.dp)
                    .height(224.5.dp)
                    .fillMaxWidth()
                    .border(width = 1.dp, Color(0xFFA0A0A0), shape = RoundedCornerShape(7.dp))
                    .background(color = Color.White, shape = RoundedCornerShape(7.dp))
                ){
                    MiniCalendar(todoViewModel = viewModel, modifier = Modifier.fillMaxSize())
                }
            }

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 25.dp, end = 20.dp, bottom = 20.dp)
                .background(color = Color(0xFFBDCEBD), shape = RoundedCornerShape(7.dp))
            ){
                Column(modifier = Modifier.fillMaxSize()){
                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(text = todayText, fontSize = 18.sp, fontFamily = NanumGothic, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, top = 15.dp, bottom = 15.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "할일목록수정",
                            modifier = Modifier
                                .padding(top = 10.5.dp, end = 19.dp)
                                .size(30.dp)
                                .clickable(
                                    interactionSource = remember {MutableInteractionSource()},
                                    indication = null
                                ){onIconClick()}
                        )
                    }

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(7.dp))
                    ){
                        LazyColumn(modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ){
                            items(items = todoList, key = {item -> item.id}){ item ->
                                ToDoItemCard(title = item.title, time = item.time, isChecked = item.isChecked, onCheckedChange = {viewModel.toggleCheck(item.id)})
                            }
                        }
                    }
                }
            }
        }
    }
}