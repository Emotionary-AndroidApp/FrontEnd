package com.example.emotionary

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.emotionary.ui.theme.EmotionaryTheme

class DiaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmotionaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiaryScreen()
                }
            }
        }
    }
}

@Composable
fun DiaryScreen() {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.diary_background),
            contentDescription = "다이어리 배경화면",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column {
            Image(
                painter = painterResource(id = R.drawable.back_btn),
                contentDescription = "뒤로가기 버튼",
                modifier = Modifier.size(25.dp)
                    .offset(x = 25.dp, y = 40.dp)
                    .clickable {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
            )
        }
    }
}