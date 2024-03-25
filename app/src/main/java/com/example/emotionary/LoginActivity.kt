package com.example.emotionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.emotionary.ui.theme.EmotionaryTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmotionaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen(){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = "로그인 배경화면", // 접근성을 위한 설명
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // 이미지가 Box를 꽉 채우도록 조정
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // Column 내부 요소들을 가로 방향으로 중앙에 배치
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(240.dp)) // 상단 이미지와의 간격 조정

        Image(
            painter = painterResource(id = R.drawable.emotionarytitle),
            contentDescription = "로그인 타이틀",
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.size(80.dp)) // 텍스트와 이미지 사이의 간격 조정

        Text(text = "login with kakao")

        Spacer(modifier = Modifier.size(20.dp)) // 카카오톡 로그인 이미지와의 간격 조정

        Image(
            painter = painterResource(id = R.drawable.kakaotalklogo),
            contentDescription = "카카오톡 로그인",
            modifier = Modifier.size(60.dp)
        )
    }
}
