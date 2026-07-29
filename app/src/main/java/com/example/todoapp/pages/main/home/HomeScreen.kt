package com.example.todoapp.pages.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 12.dp)
                    )
                }
            }

            if(safeProgress < 0.5f){
                Row(modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if(safeProgress > 0f){
                        Spacer(modifier = Modifier.fillMaxWidth(safeProgress))
                    }

                    Text(text = "$percentage%",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "0%", fontSize = 10.sp, fontFamily = FontFamily.SansSerif, color = Color.Black, modifier = Modifier.padding(horizontal = 8.dp))
            Text(text = "100%", fontSize = 10.sp, fontFamily = FontFamily.SansSerif, color = Color.Black, modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}

@Composable
@Preview
fun HomeScreen() {
    var sliderPosition by remember{mutableFloatStateOf(30f)}
    var achieveDays by remember{mutableIntStateOf(0)}
    val todayText = remember{
        val formatter = SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.KOREAN)
        formatter.format(Date())
    }

    Scaffold(){
        innerPadding -> Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)){

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "김이독 님", fontSize = 25.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 20.dp, top = 35.dp, bottom = 15.dp)
                    )

                    Box(modifier = Modifier
                        .padding(start = 20.dp, end = 13.dp)
                        .height(105.dp)
                        .border(width = 1.dp, Color(0xFF444F34), shape = RoundedCornerShape(7.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(7.dp))
                    ){
                        Column(modifier = Modifier.fillMaxSize()){
                            Text(text = "오늘 달성률", fontSize = 18.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 12.dp, top = 12.dp)
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
                            Text(text = "${achieveDays}일 ", fontSize = 15.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold,
                                style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                                modifier = Modifier
                            )

                            Text(text = "연속 To-Do 달성!", fontSize = 15.sp, fontFamily = FontFamily.SansSerif,
                                style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                                modifier = Modifier
                            )
                        }
                    }
                }

                Box(modifier = Modifier
                    .weight(1f)
                    .padding(top = 35.dp, end = 20.dp)
                    .height(226.5.dp)
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
                        Text(text = todayText, fontSize = 18.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "할일목록수정",
                            modifier = Modifier
                                .padding(top = 9.dp, end = 16.dp)
                                .size(30.dp)
                        )
                    }

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(7.dp))
                    )
                }
            }
        }
    }
}