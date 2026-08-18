package com.example.todoapp.ui.theme

import com.example.todoapp.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with

val NanumGothic = FontFamily(

    Font(
        resId = R.font.nanum_gothic,
        weight = FontWeight.Normal
    ),

    Font(
        resId = R.font.nanum_gothic_bold,
        weight = FontWeight.Bold
    )

)

val Typography = Typography(
    // 일반 텍스트
    bodyLarge = TextStyle(
        fontFamily = NanumGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
    ),
    // 텍스트 필드
    bodyMedium = TextStyle(
        fontFamily = NanumGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
            ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 50.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = NanumGothic
    ),
    // 버튼
    labelLarge = TextStyle(
        fontFamily = NanumGothic,
        fontWeight = FontWeight.Medium
    ),
    // 네비게이션 바
    labelMedium = TextStyle(
        fontFamily = NanumGothic,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp
    ),
    // AlertDialog 제목
    headlineSmall = TextStyle(
        fontFamily = NanumGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 0.5.sp
    ),


)

//val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
//)