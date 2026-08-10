package com.example.todoapp.pages.main.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import com.example.todoapp.pages.main.home.ToDoItemData
import com.example.todoapp.pages.main.home.ScheduleEditScreen
import com.example.todoapp.ui.theme.NanumGothic

@Composable
fun ToDoItemCard(title: String, time: String? = null, modifier: Modifier = Modifier){
    var isChecked by remember {mutableStateOf(false)}

    Row(modifier = Modifier.fillMaxWidth()){
        CompositionLocalProvider(LocalRippleConfiguration provides RippleConfiguration(Color(0xFFF2F8E3))){
            Checkbox(checked = isChecked, onCheckedChange = {isChecked = it},
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
                .padding(start = 3.dp, top = 14.dp)
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
                    .padding(top = 14.5.dp, end = 30.dp)
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
            .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "0%", fontSize = 10.sp, fontFamily = NanumGothic, color = Color.Black, modifier = Modifier.padding(horizontal = 8.dp))
            Text(text = "100%", fontSize = 10.sp, fontFamily = NanumGothic, color = Color.Black, modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}

@Composable
@Preview
fun HomeScreen(onIconClick: () -> Unit = {}, viewModel: TodoViewModel = viewModel()) {
    var sliderPosition by remember{mutableFloatStateOf(75f)}
    var achieveDays by remember{mutableIntStateOf(0)}
    val todayText = remember{
        val formatter = SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.KOREAN)
        formatter.format(Date())
    }
    var todoList = viewModel.todoList

    Scaffold(){
        innerPadding -> Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ){

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "김이독 님", fontSize = 25.sp, fontFamily = NanumGothic, fontWeight = FontWeight.Bold,
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
                                    .padding(start = 12.dp, top = 14.dp, bottom = 5.dp)
                            )

                            CustomProgressBar(
                                progress = sliderPosition / 100f,
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
                    .height(231.5.dp)
                    .fillMaxWidth()
                    .border(width = 1.dp, Color(0xFFA0A0A0), shape = RoundedCornerShape(7.dp))
                    .background(color = Color.White, shape = RoundedCornerShape(7.dp))
                )
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
                                .padding(top = 9.5.dp, end = 18.dp)
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
                            item{
                                ToDoItemCard("영어 단어 10개 외우기", "07:00", modifier = Modifier)
                            }

                            item{
                                ToDoItemCard("자소서 수정하기", modifier = Modifier)
                            }

                            item{
                                ToDoItemCard("뮤지컬 티켓팅 하기", "18:45", modifier = Modifier)
                            }

                            item{
                                ToDoItemCard("동아리 회의하기", "23:00", modifier = Modifier)
                            }

                            items(todoList){ item ->
                                ToDoItemCard(title = item.title, time = item.time)
                            }
                        }
                    }
                }
            }
        }
    }
}