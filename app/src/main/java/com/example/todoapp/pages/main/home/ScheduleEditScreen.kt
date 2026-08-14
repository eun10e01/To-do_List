package com.example.todoapp.pages.main.home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
    val id: Long = System.currentTimeMillis() + (0..1000).random(),
    val title: String,
    val time: String? = null,
    val isChecked: Boolean = false
)

@Composable
fun ToDoItemEdit(
    title: String,
    time: String? = null,
    onClick: () -> Unit = {},
    onDragStart: () -> Unit = {},
    onDrag: (changeY: Float) -> Unit = {},
    onDragEnd: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
        Icon(imageVector = Icons.Default.UnfoldMore, contentDescription = "이동",
            tint = Color.Black,
            modifier = Modifier
                .padding(start = 20.dp, top = 5.dp, bottom = 5.dp)
                .pointerInput(Unit){
                    detectDragGestures(
                        onDragStart = {onDragStart()},
                        onDragEnd = {onDragEnd()},
                        onDragCancel = {onDragEnd()},
                        onDrag = {change, dragAmount ->
                            change.consume()
                            onDrag(dragAmount.y)
                        }
                    )
                }
        )

        Row(modifier = Modifier
            .weight(1f)
            .clickable {onClick()},
        ){
            Text(text = title, fontSize = 15.sp, fontFamily = NanumGothic, fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp, top = 5.dp, bottom = 12.dp)
            )

            if(time != null){
                Text(text = time, fontSize = 15.sp, fontFamily = NanumGothic, fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 5.dp, end = 30.dp)
                )
            }
        }
    }

    HorizontalDivider(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp),
        thickness = 0.3.dp,
        color = Color.Black
    )
}

@Composable
fun ScheduleEditScreen(navController: NavHostController, viewModel: TodoViewModel = viewModel()){
    val context = LocalContext.current
    val todayText = remember {
        val formatter = SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.KOREAN)
        formatter.format(Date())
    }

    val dateFormat = remember {SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN)}
    val todayDateString = remember {dateFormat.format(Date())}

    val todoList = viewModel.todoList

    var showAddDialog by remember {mutableStateOf(false)}
    var newTitle by remember {mutableStateOf("")}
    var newTime by remember {mutableStateOf("")}

    var startDate by remember {mutableStateOf(todayDateString)}
    var endDate by remember {mutableStateOf(todayDateString)}
    var isRepeat by remember {mutableStateOf(false)}
    var repeatOption by remember {mutableStateOf("매일")} //매일, 매주, 매월

    val isRange = startDate != endDate //날짜 자동 감지 (시작일 != 종료일 이면 기한)

    var selectedItemForEdit by remember {mutableStateOf<ToDoItemData?>(null)}
    var editTitle by remember {mutableStateOf("")}
    var editTime by remember {mutableStateOf("")}
    var editStartDate by remember {mutableStateOf(todayDateString)}
    var editEndDate by remember {mutableStateOf(todayDateString)}
    var editIsRepeat by remember {mutableStateOf(false)}
    var editRepeatOption by remember {mutableStateOf("매일")}
    val editIsRange = editStartDate != editEndDate

    var showTimePicker by remember {mutableStateOf(false)}
    var isEditingTime by remember {mutableStateOf(false)}

    var draggedItemIndex by remember {mutableStateOf<Int?>(null)}
    var offsetY by remember {mutableFloatStateOf(0f)}

    fun resetAddDialog(){
        newTitle = ""
        newTime = ""
        startDate = todayDateString
        endDate = todayDateString
        isRepeat = false
        repeatOption = "매일"
        showAddDialog = false
    }

    Scaffold(){
        innerPadding -> Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ){
            IconButton(
                onClick = {navController.popBackStack()},
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
                            modifier = Modifier
                                .padding(start = 16.dp, top = 15.dp, bottom = 15.dp)
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
                            itemsIndexed(items = todoList, key = {_, item -> item.id }){index, item ->
                                ToDoItemEdit(
                                    title = item.title,
                                    time = item.time,
                                    onClick = {
                                        selectedItemForEdit = item
                                        editTitle = item.title
                                        editTime = item.time ?: ""
                                    },
                                    onDragStart = {draggedItemIndex = index},
                                    onDrag = {changeY -> offsetY += changeY
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
                                        targetIndex?.let {newIndex ->
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

                        IconButton(onClick = {showAddDialog = true},
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color(0xFF858677),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(end = 5.dp, bottom = 5.dp)
                                .align(Alignment.BottomEnd)
                        ){
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "추가하기")
                        }
                    }
                }
            }
        }

        if(showAddDialog){
            AlertDialog(onDismissRequest = {resetAddDialog()},
                modifier = Modifier.border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(16.dp)
                ),
                containerColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                titleContentColor = Color.Black,
                textContentColor = Color.Black,
                title = {Text(text = "To-do 추가", fontWeight = FontWeight.Bold)},
                text = {
                    Column(modifier = Modifier
                        .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ){
                        OutlinedTextField(
                            value = newTitle,
                            onValueChange = {newTitle = it},
                            label = {Text("제목")},
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .clickable{
                                isEditingTime = false
                                showTimePicker = true
                            }
                        ){
                            OutlinedTextField(
                                value = newTime,
                                onValueChange = {},
                                readOnly = true,
                                label = {Text("시간")},
                                placeholder = {Text("00:00")},
                                modifier = Modifier.fillMaxWidth(),
                                enabled = false,
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledBorderColor = Color.Gray,
                                    disabledTextColor = Color.Black,
                                    disabledLabelColor = Color.Gray
                                ),
                                singleLine = true
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Box(modifier = Modifier
                                .weight(1f)
                                .clickable{
                                    val cal = Calendar.getInstance()
                                    DatePickerDialog(
                                        context,
                                        {_, year, month, day ->
                                            cal.set(year, month, day)
                                            startDate = dateFormat.format(cal.time)
                                        },
                                        cal.get(Calendar.YEAR),
                                        cal.get(Calendar.MONTH),
                                        cal.get(Calendar.DAY_OF_MONTH)
                                    ).show()
                                }
                            ){
                                OutlinedTextField(
                                    value = startDate,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = {Text("시작 날짜")},
                                    enabled = false,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        disabledBorderColor = Color.Gray,
                                        disabledTextColor = Color.Black,
                                        disabledLabelColor = Color.Gray
                                    ),
                                    singleLine = true
                                )
                            }

                            Text(text = "~", modifier = Modifier.padding(horizontal = 6.dp), fontWeight = FontWeight.Bold)

                            Box(modifier = Modifier
                                .weight(1f)
                                .clickable{
                                    val cal = Calendar.getInstance()
                                    DatePickerDialog(
                                        context,
                                        {_, year, month, day ->
                                            cal.set(year, month, day)
                                            endDate = dateFormat.format(cal.time)
                                        },
                                        cal.get(Calendar.YEAR),
                                        cal.get(Calendar.MONTH),
                                        cal.get(Calendar.DAY_OF_MONTH)
                                    ).show()
                                }
                            ){
                                OutlinedTextField(
                                    value = endDate,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = {Text("종료 날짜")},
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

                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ){
                            RadioButton(
                                selected = !isRange,
                                onClick = null, //클릭 불가
                                colors = RadioButtonDefaults.colors(
                                    disabledSelectedColor = Color.Black,
                                    disabledUnselectedColor = Color.Gray
                                ),
                                enabled = false
                            )
                            Text("일반", fontSize = 14.sp)

                            Spacer(modifier = Modifier.width(16.dp))

                            RadioButton(
                                selected = isRange,
                                onClick = null, //클릭 불가
                                colors = RadioButtonDefaults.colors(
                                    disabledSelectedColor = Color.Black,
                                    disabledUnselectedColor = Color.Gray
                                ),
                                enabled = false
                            )
                            Text("기한", fontSize = 14.sp)
                        }

                        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)

                        Row(verticalAlignment = Alignment.CenterVertically){
                            Checkbox(checked = isRepeat, onCheckedChange = {isRepeat = it})
                            Text("반복", fontWeight = FontWeight.Medium, fontSize = 14.sp)
                        }

                        if(isRepeat){
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                listOf("매일", "매주", "매월").forEach{option ->
                                    Row(verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.clickable {repeatOption = option}
                                    ){
                                        RadioButton(
                                            selected = (repeatOption == option),
                                            onClick = {repeatOption = option}
                                        )
                                        Text(option, fontSize = 13.sp)
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        if(newTitle.isNotBlank()){
                            viewModel.addTodo(
                                title = newTitle,
                                time = if(newTime.isNotBlank()) newTime else null
                            )
                            resetAddDialog()
                        }
                    }){
                        Text("추가")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {resetAddDialog()}){
                        Text("취소")
                    }
                }
            )
        }

        selectedItemForEdit?.let {item ->
            AlertDialog(
                onDismissRequest = {selectedItemForEdit = null},
                modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(16.dp)),
                containerColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                title = {Text(text = "To-do 수정", fontWeight = FontWeight.Bold)},
                text = {
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ){
                        OutlinedTextField(
                            value = editTitle,
                            onValueChange = {editTitle = it},
                            label = {Text("제목")},
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isEditingTime = true
                                showTimePicker = true
                            }
                        ){
                            OutlinedTextField(
                                value = editTime,
                                onValueChange = {},
                                readOnly = true,
                                label = {Text("시간")},
                                placeholder = {Text("00:00")},
                                modifier = Modifier.fillMaxWidth(),
                                enabled = false,
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledBorderColor = Color.Gray,
                                    disabledTextColor = Color.Black,
                                    disabledLabelColor = Color.Gray
                                ),
                                singleLine = true
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Box(modifier = Modifier
                                .weight(1f)
                                .clickable{
                                    val cal = Calendar.getInstance()
                                    DatePickerDialog(
                                        context,
                                        {_, year, month, day ->
                                            cal.set(year, month, day)
                                            editStartDate = dateFormat.format(cal.time)
                                        },
                                        cal.get(Calendar.YEAR),
                                        cal.get(Calendar.MONTH),
                                        cal.get(Calendar.DAY_OF_MONTH)
                                    ).show()
                                }
                            ){
                                OutlinedTextField(
                                    value = editStartDate,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = {Text("시작 날짜")},
                                    enabled = false,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        disabledBorderColor = Color.Gray,
                                        disabledTextColor = Color.Black,
                                        disabledLabelColor = Color.Gray
                                    ),
                                    singleLine = true
                                )
                            }

                            Text(text = "~", modifier = Modifier.padding(horizontal = 6.dp), fontWeight = FontWeight.Bold)

                            Box(modifier = Modifier
                                    .weight(1f)
                                    .clickable{
                                        val cal = Calendar.getInstance()
                                        DatePickerDialog(
                                            context,
                                            { _, year, month, day ->
                                                cal.set(year, month, day)
                                                editEndDate = dateFormat.format(cal.time)
                                            },
                                            cal.get(Calendar.YEAR),
                                            cal.get(Calendar.MONTH),
                                            cal.get(Calendar.DAY_OF_MONTH)
                                        ).show()
                                    }
                            ){
                                OutlinedTextField(
                                    value = editEndDate,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = {Text("종료 날짜")},
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

                        Row(verticalAlignment = Alignment.CenterVertically){
                            RadioButton(
                                selected = !editIsRange,
                                onClick = null,
                                colors = RadioButtonDefaults.colors(
                                    disabledSelectedColor = Color.Black,
                                    disabledUnselectedColor = Color.Gray
                                ),
                                enabled = false
                            )
                            Text("일반", fontSize = 14.sp)

                            Spacer(modifier = Modifier.width(16.dp))

                            RadioButton(
                                selected = editIsRange,
                                onClick = null,
                                colors = RadioButtonDefaults.colors(
                                    disabledSelectedColor = Color.Black,
                                    disabledUnselectedColor = Color.Gray
                                ),
                                enabled = false
                            )
                            Text("기한", fontSize = 14.sp)
                        }

                        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = editIsRepeat, onCheckedChange = {editIsRepeat = it})
                            Text("반복", fontWeight = FontWeight.Medium, fontSize = 14.sp)
                        }

                        if(editIsRepeat){
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                listOf("매일", "매주", "매월").forEach {option ->
                                    Row(verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.clickable { editRepeatOption = option }
                                    ){
                                        RadioButton(
                                            selected = (editRepeatOption == option),
                                            onClick = {editRepeatOption = option}
                                        )
                                        Text(option, fontSize = 13.sp)
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Row{TextButton(onClick = {
                        viewModel.deleteTodo(item.id)
                        selectedItemForEdit = null
                        }){
                            Text("삭제", color = Color.Red, fontWeight = FontWeight.Bold)
                        }

                        TextButton(onClick = {
                            if(editTitle.isNotBlank()){
                                viewModel.updateTodo(
                                    id = item.id,
                                    newTitle = editTitle,
                                    newTime = if(editTime.isNotBlank()) editTime else null
                                )
                                selectedItemForEdit = null
                            }
                        }){
                            Text("저장")
                        }
                    }
                },
                dismissButton = {
                    TextButton(onClick = {selectedItemForEdit = null}){
                        Text("취소")
                    }
                }
            )
        }

        if(showTimePicker){
            val calendar = Calendar.getInstance()

            TimePickerDialog(
                context,
                {_, hourOfDay, minute ->
                    val formattedTime = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        hourOfDay,
                        minute
                    )
                    if(isEditingTime){
                        editTime = formattedTime
                    }
                    else{
                        newTime = formattedTime
                    }

                    showTimePicker = false
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).apply{
                setOnCancelListener {showTimePicker = false}
                show()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleEditScreenPreview(){
    val navController = rememberNavController()
    ScheduleEditScreen(navController = navController)
}