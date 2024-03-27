package com.example.emotionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.emotionary.ui.theme.EmotionaryTheme

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 인텐트에서 사용자 이름을 추출
        val userName = intent.getStringExtra("userName")
        val userProfile = intent.getStringExtra("userProfile")
        setContent {
            EmotionaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(userName,userProfile)
                }
            }
        }
    }
}

@Composable
fun MainScreen(userName: String?, userProfile: String?){

    Column {
        Spacer(modifier = Modifier.height(62.dp))
        Row {
            Spacer(modifier = Modifier.width(200.dp))
            Image(
                painter = painterResource(id = R.drawable.blue_underline),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Fit
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.home_background),
            contentDescription = "홈 배경화면", // 접근성을 위한 설명
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(50.dp))

            Row (modifier = Modifier.fillMaxSize()){
                Spacer(modifier = Modifier.width(50.dp))

                Image(
                    painter = rememberImagePainter("$userProfile"),
                    contentDescription ="사용자 프로필 사진",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillBounds
                )

                Spacer(modifier = Modifier.width(50.dp))

                Column{
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "$userName 의",
                        fontSize = 28.sp,
                        fontFamily = FontFamily(
                            Font(R.font.garamflower)
                        ),
                        fontWeight = FontWeight.Bold
                        )
                    Row {
                        Spacer(modifier = Modifier.width(30.dp))
                        Text(text = "1월 일기",
                            fontSize = 35.sp,
                            fontFamily = FontFamily(
                                Font(R.font.garamflower)
                            ),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

    }

}