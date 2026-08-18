package com.example.todoapp.pages.main.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.dto.NoticeResponse
import com.example.todoapp.viewmodel.NoticeViewModel

@Composable
fun NoticeScreen(
    viewModel: NoticeViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadNotices()
    }

    val notices = viewModel.notices

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (notices.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "등록된 공지사항이 없습니다.",
                    color = Color.Gray
                )
            }
        } else {
            notices.forEach { notice ->
                NoticeItem(notice)
            }
        }
    }
}

@Composable
fun NoticeItem(
    notice: NoticeResponse
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                }
                .padding(
                    horizontal = 10.dp,
                    vertical = 15.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = notice.title,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = notice.createdAt.substringBefore("T"),
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = if (expanded) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                contentDescription = if (expanded) {
                    "공지사항 접기"
                } else {
                    "공지사항 펼치기"
                },
                tint = Color.Gray
            )
        }

        if (expanded) {
            Text(
                text = notice.content,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDADADA))
                    .padding(
                        horizontal = 16.dp,
                        vertical = 20.dp
                    ),
                lineHeight = 20.sp,
                color = Color.Black
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 10.dp),
            color = Color(0xFFDADADA))
    }
}