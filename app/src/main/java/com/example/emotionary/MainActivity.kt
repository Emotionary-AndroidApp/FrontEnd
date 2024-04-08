package com.example.emotionary

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.emotionary.ui.theme.EmotionaryTheme
import java.time.LocalDate

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
                    HiddenPageBtn()
                }
            }
        }
    }
}

@Composable
fun Calendar(month: Int, year: Int) {
    val daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth()
    val firstDayOfMonth = LocalDate.of(year, month, 1).dayOfWeek.value
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    // 선택된 날짜의 인덱스를 추적하는 상태. 초기값은 -1로 설정하여 어떤 날짜도 선택되지 않은 상태를 나타냄
    var selectedDayIndex by remember { mutableStateOf(-1) }
    var showDialog by remember { mutableStateOf(false) } // 팝업 표시 여부
    var selectedDay by remember { mutableStateOf(1) } // 선택된 날짜
    val context = LocalContext.current // 현재 context

    if(showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false},
            title = { Text(text = "$selectedDay 일 일기",
                fontFamily = FontFamily(Font(R.font.garamflower)))},
            text = {
                   Text(text = "일기 세부 내용 작성",
                       fontFamily = FontFamily(Font(R.font.garamflower)),
                       fontWeight = FontWeight.Bold
                   )
            },
            confirmButton = { Button(onClick = {showDialog = false}) {
                Text(text = "X",
                    fontFamily = FontFamily(Font(R.font.garamflower))
                ) }
            },
            dismissButton = {
                Button(onClick = {
                    showDialog = false
                    val intent = Intent(context, DiaryActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Text("🖋️")
                }
            },
            containerColor = Color.White
        )
    }

    Column {
        // 요일 헤더
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween // 요일 간격 유지
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.garamflower))
                )
            }
        }
        // 날짜 그리드
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp), // 칸 간 수직 간격
            horizontalArrangement = Arrangement.SpaceBetween // 칸 간 수평 간격 조정
        ) {
            items(firstDayOfMonth - 1) {
                // 월의 첫 번째 날짜 이전 빈 칸 채우기
                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                        .background(Color.Transparent)
                )
            }
            items(daysInMonth) { index ->
                val day = index + 1
                val isSelected = index == selectedDayIndex
                // 실제 날짜
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp) // 이모티콘을 위한 크기 조정
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(10.dp),
                                clip = true
                            )
                            .border(
                                1.dp,
                                if (isSelected) Color.Black else Color.Gray,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                selectedDayIndex = index // 선택한 날짜의 인덱스를 업데이트
                                selectedDay = day
                                showDialog = true // 팝업
                            }
                            .background(
                                if (isSelected) Color(0xFFF5F5F5) else Color(0xFFD9D9D9),
                                shape = RoundedCornerShape(10.dp)
                            ), // 색상 및 둥근 모서리 적용
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🗓") // 예시 이모티콘
                    }
                    Spacer(modifier = Modifier.height(7.dp))
                    // 날짜 표시
                    Text(
                        text = "$day",
                        fontFamily = FontFamily(Font(R.font.garamflower)),
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.Black else Color.Gray
                    )
                }
            }
        }
    }
} // 캘린더 함수

@Composable
fun HiddenPageBtn() {
    val context = LocalContext.current // 현재 컨텍스트 가져옴

    Column (modifier = Modifier.padding(30.dp)){ // 히든페이지로 넘어가는 버튼
        Row {
            Spacer(modifier = Modifier.width(310.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape,
                        clip = false
                    )
                    .clickable {
                        val intent = Intent(context, HiddenActivity::class.java)
                        context.startActivity(intent)
                    }
                    .clip(CircleShape)
                    .background(
                        Color.White
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🏠",
                    fontSize = 20.sp)
            }
        }
    }
} // 히든 페이지로 넘어가는 버튼 함수

@Composable
fun ProgessBar() {
    val progress = remember { mutableStateOf(0.7f) } // 목표 달성 바의 진행 상태를 나타내는 변수 -> 예시로 0.7(70%)

    // 배경 컨테이너
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(25.dp)
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .background(
                color = Color(0xFFBECEBE),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
            ) // 배경 색상과 둥근 모서리 설정
    ) {
        // 실제 진행 상태를 나타내는 바
        Box(modifier = Modifier
            .fillMaxWidth(progress.value)
            .height(25.dp)
            .background(
                color = Color(0xFFE7F8E7),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
            )
        )
    }
}

@Composable
fun MainScreen(userName: String?, userProfile: String?) {
    // 현재 날짜 상태 관리
    val calendarState = remember {
        mutableStateOf(LocalDate.now())
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.home_background),
            contentDescription = "홈 배경화면", // 접근성을 위한 설명
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column {
            Spacer(modifier = Modifier.height(50.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(50.dp))
                Image(
                    painter = rememberImagePainter("$userProfile"),
                    contentDescription = "사용자 프로필 사진",
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .border(6.dp, Color.White, CircleShape),
                    contentScale = ContentScale.FillBounds
                )

                Spacer(modifier = Modifier.width(50.dp))

                Column(modifier = Modifier.height(100.dp)) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.width(100.dp)) {
                        Box(
                            modifier = Modifier.weight(1f), // Box가 사용 가능한 공간을 채우도록 설정
                            contentAlignment = Alignment.Center // Box 내부의 컴포넌트를 중앙에 배치
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.blue_underline),
                                contentDescription = "이름 밑줄",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center)
                                    .size(20.dp)
                            )
                            Text(
                                text = "$userName",
                                fontSize = 28.sp,
                                fontFamily = FontFamily(Font(R.font.garamflower)),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "의",
                            fontSize = 28.sp,
                            fontFamily = FontFamily(Font(R.font.garamflower))
                        )
                    }
                    Row {
                        Text(
                            text = "<",
                            modifier = Modifier.clickable { // 누르면 이전 달로 이동
                                calendarState.value = calendarState.value.minusMonths(1)
                            },
                            fontSize = 35.sp,
                            fontFamily = FontFamily(Font(R.font.garamflower))
                        )
                        Spacer(modifier = Modifier.width(25.dp))
                        Text(
                            text = "${calendarState.value.monthValue}월 일기",
                            fontSize = 35.sp,
                            fontFamily = FontFamily(Font(R.font.garamflower))
                        )
                        Spacer(modifier = Modifier.width(25.dp))
                        Text(
                            text = ">",
                            modifier = Modifier.clickable { // 누르면 다음 달로 이동
                                calendarState.value = calendarState.value.plusMonths(1)
                            },
                            fontSize = 35.sp,
                            fontFamily = FontFamily(Font(R.font.garamflower))
                        )
                    }
                }
            }

            Box( // 목표진행도
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            ) {
                Column (
                    modifier=Modifier.fillMaxSize()
                ){
                    Row(Modifier.width(200.dp)) {
                        Spacer(modifier = Modifier.width(30.dp))
                        Text(
                            text = "목표진행도",
                            fontFamily = FontFamily(
                                Font(R.font.garamflower)
                            ),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Column {
                            Box(
                                modifier = Modifier.width(100.dp),
                                contentAlignment = Alignment.Center // Box 내부의 컴포넌트를 중앙에 배치
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.yellow_underline),
                                    contentDescription = "디데이 밑줄",
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(40.dp)
                                )
                                Text(
                                    text = "D - 9",
                                    fontFamily = FontFamily(
                                        Font(R.font.garamflower)
                                    ),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                    }
                    Column(modifier = Modifier.padding(top = 25.dp)) {
                        // 목표 달성 바
                        ProgessBar()
                    }
                }
            }

            Box(modifier = Modifier // 캘린더 + 다이어리
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 15.dp, end = 15.dp)) {
                Column(modifier = Modifier
                    .fillMaxSize()) { // 스크롤 효과 적용 - 캘린더와 다이어리 column이 scroll되도록
                    Calendar(month = calendarState.value.monthValue, year = calendarState.value.year) // 캘린더
                    // 해당 날짜 다이어리 보여주는 함수 작성 예정
                }
            }
        }
    }
}

