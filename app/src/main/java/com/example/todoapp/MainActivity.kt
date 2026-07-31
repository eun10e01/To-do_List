//화면 시작점
package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
//import com.example.todoapp.pages.auth.LoginScreen
import com.example.todoapp.pages.main.MainFrameScreen
import com.example.todoapp.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
                    MainFrameScreen()
                }
            }
            // 로그인 화면_이후 페이지 연결에 사용하려고 주석처리해둠
//            LoginScreen(
//                onSignUpClick = {
//                    navController.navigate("signup")
//                },
//                onFindAccountClick = {
//                    navController.navigate("findAccount")
//                }
//            )
        }
    }
}