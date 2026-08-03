package com.example.todoapp.pages.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ToDoItemData(val title: String, val time: String? = null)

@Composable
fun ToDoItemEdit(title: String, time: String? = null, modifier: Modifier = Modifier){
    var isChecked by remember {mutableStateOf(false)}

    Row(modifier = Modifier.fillMaxWidth()){
        Icon(
            imageVector = Icons.Default.UnfoldMore,
            contentDescription = "이동",
            tint = Color.Black,
            modifier = Modifier.padding(start = 20.dp, top = 5.dp, bottom = 5.dp)
        )

        Text(
            text = title,
            fontSize = 15.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            color = if (isChecked) Color.Gray else Color.Black,
            textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp, top = 5.dp)
        )

        if (time != null) {
            Text(
                text = time,
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                color = if (isChecked) Color.Gray else Color.Black,
                textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
                modifier = Modifier
                    .padding(top = 8.dp, end = 30.dp)
            )
        }
    }

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        thickness = 0.3.dp,
        color = Color.Black
    )
}

@Composable
@Preview
fun ScheduleEditScreen() {
    val todayText = remember{
        val formatter = SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.KOREAN)
        formatter.format(Date())
    }
    var todoList = remember{mutableStateListOf<ToDoItemData>()}

    Scaffold(){
        innerPadding -> Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ){
            IconButton(onClick = { /*뒤로 가기 동작*/ },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier.padding(start = 5.dp, top = 8.dp)
            ){
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로 가기"
                )
            }

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 20.dp)
                .background(color = Color(0xFFBDCEBD), shape = RoundedCornerShape(7.dp))
            ){
                Column(modifier = Modifier.fillMaxSize()){
                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(text = todayText, fontSize = 18.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp)
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
                                ToDoItemEdit("영어 단어 10개 외우기", "07:00", modifier = Modifier)
                            }

                            item{
                                ToDoItemEdit("자소서 수정하기", modifier = Modifier)
                            }

                            item{
                                ToDoItemEdit("뮤지컬 티켓팅 하기", "18:45", modifier = Modifier)
                            }

                            item {
                                ToDoItemEdit("동아리 회의하기", "23:00", modifier = Modifier)
                            }

                            items(todoList){ item ->
                                ToDoItemEdit(title = item.title, time = item.time)
                            }
                        }

                        IconButton(onClick = { /*추가하기 동작*/ },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color(0xFF858677),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(end = 5.dp, bottom = 5.dp)
                                .align(Alignment.BottomEnd)
                        ){
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "추가하기"
                            )
                        }
                    }
                }
            }
        }
    }
}