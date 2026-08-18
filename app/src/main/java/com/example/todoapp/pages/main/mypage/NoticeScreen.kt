package com.example.todoapp.pages.main.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.example.todoapp.navigation.Screen
import com.example.todoapp.viewmodel.MyPageViewModel

data class NoticeItem(
    val title: String,
    val date: String,
    val content: String
)

@Composable
fun NoticeScreen() {
    val notices = listOf(
        NoticeItem(
            title = "첫 번째 공지사항입니다.",
            date = "2026.08.19",
            content = "공지사항의 상세 내용이 여기에 표시됩니다."
        ),
        NoticeItem(
            title = "두 번째 공지사항입니다.",
            date = "2026.08.15",
            content = "두 번째 공지사항의 내용입니다."
        ),
        NoticeItem(
            title = "세 번째 공지사항입니다.",
            date = "2026.08.10",
            content = "세 번째 공지사항의 내용입니다."
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        notices.forEach { notice ->
            var expanded by remember {
                mutableStateOf(false)
            }

            NoticeItem(
                notice = notice,
                expanded = expanded,
                onClick = {
                    expanded = !expanded
                }
            )
        }
    }
}

@Composable
fun NoticeItem(
    notice: NoticeItem,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        // 제목 + 날짜 + 펼침 아이콘
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(
                    horizontal = 5.dp,
                    vertical = 14.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = notice.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = notice.date,
                    fontSize = 13.sp,
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
                tint = Color.Gray,
                modifier = Modifier.size(28.dp)
            )
        }

        HorizontalDivider(
            color = Color(0xFFA0A0A0)
        )

        // 펼쳐졌을 때만 내용 표시
        if (expanded) {
            Text(
                text = notice.content,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 20.dp
                    ),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = Color.Black
            )

            HorizontalDivider(
                color = Color(0xFFA0A0A0)
            )
        }
    }
}