package com.example.todoapp.pages.main.home

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.theme.NanumGothic
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class ToDoItemData(
    val id: Long = System.currentTimeMillis() + (0..1000).random(), //고유 식별자
    val title: String,
    val time: String? = null,
    val isChecked: Boolean = false
)

@Composable
fun ToDoItemEdit(title: String, time: String? = null, onDragStart: () -> Unit = {}, onDrag: (changeY: Float) -> Unit = {}, onDragEnd: () -> Unit = {}, modifier: Modifier = Modifier){
    Row(modifier = Modifier.fillMaxWidth()){
        Icon(
            imageVector = Icons.Default.UnfoldMore,
            contentDescription = "이동",
            tint = Color.Black,
            modifier = Modifier
                .padding(start = 20.dp, top = 5.dp, bottom = 5.dp)
                .pointerInput(Unit){
                    detectDragGestures(
                        onDragStart = {onDragStart()},
                        onDragEnd = {onDragEnd()},
                        onDragCancel = {onDragEnd()},
                        onDrag = {change, dragAmount -> change.consume(); onDrag(dragAmount.y)}
                    )
                }
        )

        Text(
            text = title,
            fontSize = 15.sp,
            fontFamily = NanumGothic,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp, top = 5.dp, bottom = 12.dp)
        )

        if (time != null) {
            Text(
                text = time,
                fontSize = 15.sp,
                fontFamily = NanumGothic,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 5.dp, end = 30.dp)
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
fun ScheduleEditScreen(navController: NavHostController, viewModel: TodoViewModel = viewModel()) {
    val todayText = remember{
        val formatter = SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.KOREAN)
        formatter.format(Date())
    }
    var todoList = viewModel.todoList
    var showDialog by remember {mutableStateOf(false)} //팝업 표시 여부
    var newTitle by remember {mutableStateOf("")}
    var newTime by remember {mutableStateOf("")}
    var showTimePicker by remember {mutableStateOf(false)}
    var draggedItemIndex by remember {mutableStateOf<Int?>(null)}
    var offsetY by remember {mutableFloatStateOf(0f)}

    Scaffold(){
        innerPadding -> Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ){
            IconButton(onClick = {navController.popBackStack()},
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
                        Text(text = todayText, fontSize = 18.sp, fontFamily = NanumGothic, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, top = 15.dp, bottom = 15.dp)
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
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ){
                            itemsIndexed(items = todoList, key = {_, item -> item.id}){
                                index, item -> ToDoItemEdit(
                                    title = item.title,
                                    time = item.time,
                                    onDragStart = {draggedItemIndex = index},
                                    onDrag = {changeY ->
                                        offsetY += changeY
                                        val targetIndex =
                                            if(offsetY > 40f && index < todoList.size - 1){
                                                index + 1
                                            }
                                            else if(offsetY < -40f && index > 0){
                                                index - 1
                                            }
                                            else{
                                                null
                                            }
                                        targetIndex?.let{newIndex ->
                                            viewModel.moveItem(index, newIndex)
                                            draggedItemIndex = newIndex
                                            offsetY = 0f
                                        }
                                    },
                                    onDragEnd = {
                                        draggedItemIndex = null
                                        offsetY = 0f
                                    }
                                )
                            }
                        }

                        IconButton(onClick = {showDialog = true},
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

        if(showDialog){
            AlertDialog(onDismissRequest = {showDialog = false; newTitle = ""; newTime = ""},
                modifier = Modifier.border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(16.dp)
                ),
                containerColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                titleContentColor = Color.Black,
                textContentColor = Color.Black,
                title = {Text(text = "To-do", fontWeight = FontWeight.Bold)},
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)){
                        OutlinedTextField(
                            value = newTitle,
                            onValueChange = {newTitle = it},
                            label = {Text("제목")},
                            singleLine = true
                        )

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .clickable{showTimePicker = true}
                        ){
                            OutlinedTextField(
                                value = newTime,
                                onValueChange = {},
                                readOnly = true,
                                label = {Text("시간(선택사항)")},
                                placeholder = {Text("00:00")},
                                modifier = Modifier
                                    .fillMaxWidth(),
                                enabled = false,
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledBorderColor = Color.Gray,
                                    disabledTextColor = Color.Black,
                                    disabledLabelColor = Color.Gray
                                ),
                                singleLine = true
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        if(newTitle.isNotBlank()){
                            viewModel.addTodo(title = newTitle, time = if(newTime.isNotBlank()) newTime else null)
                            newTitle = ""
                            newTime = ""
                            showDialog = false
                        }
                    }){
                        Text("추가")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                        newTitle = ""
                        newTime = ""
                    }){
                        Text("취소")
                    }
                }
            )
        }

        if(showTimePicker){
            val context = LocalContext.current
            val calendar = Calendar.getInstance()

            TimePickerDialog(context,
                {_, hourOfDay, minute ->
                    newTime = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        hourOfDay,
                        minute
                    )
                    showTimePicker = false
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).apply {
                setOnCancelListener {showTimePicker = false}
                show()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleEditScreenPreview() {
    val navController = rememberNavController()
    ScheduleEditScreen(navController = navController)
}