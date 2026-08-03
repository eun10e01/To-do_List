package com.example.todoapp.ui.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


class DateOfBirthVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {

        val originalText = text.text

        val formattedText = buildString {

            originalText.forEachIndexed { index, char ->

                append(char)

                if (index == 3 && originalText.length > 4) {
                    append("-")
                }

                if (index == 5 && originalText.length > 6) {
                    append("-")
                }
            }
        }


        val offsetMapping = object : OffsetMapping {


            // 실제 값 -> 화면 표시 위치
            override fun originalToTransformed(offset: Int): Int {

                return when {

                    offset <= 4 -> offset

                    offset <= 6 -> offset + 1

                    else -> offset + 2
                }
            }


            // 화면 표시 위치 -> 실제 값 위치
            override fun transformedToOriginal(offset: Int): Int {

                return when {

                    offset <= 4 -> offset

                    offset <= 7 -> offset - 1

                    else -> offset - 2
                }
            }
        }


        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = offsetMapping
        )
    }
}