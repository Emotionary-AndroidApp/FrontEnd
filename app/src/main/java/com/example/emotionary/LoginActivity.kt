package com.example.emotionary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient


class LoginActivity : ComponentActivity() {
    private val TAG = "LoginActivity"

    // ActivityResultLauncher를 정의합니다. 카카오 로그인 결과를 처리합니다.
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Kakao SDK 초기화
        KakaoSdk.init(this, "93033e81e2b89a27b0afb142f9637e6c")

        setContent {
            EmotionaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(::performLogin)
                }
            }
        }
    }

    private fun performLogin() {
        // 카카오톡 설치 확인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                handleLoginResult(token, error)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                handleLoginResult(token, error)
            }
        }
    }

    private fun handleLoginResult(token: OAuthToken?, error: Throwable?) {
        if (error != null) {
            Log.e(TAG, "로그인 실패 $error")
        } else if (token != null) {
            Log.e(TAG, "로그인 성공 ${token.accessToken}")

            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    // 사용자 정보 요청 실패 처리
                    Log.e(TAG, "사용자 정보 요청 실패", error)
                } else if (user != null) {
                    // 사용자 정보 요청 성공 처리
                    Log.i(TAG, "사용자 정보 요청 성공: ${user.kakaoAccount?.profile?.profileImageUrl}")

                    saveLoginInfo(user.kakaoAccount?.profile?.nickname, user.kakaoAccount?.profile?.profileImageUrl)
                    // 메인 액티비티로 이동하면서 사용자 이름 전달
                    val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                        putExtra("userName", user.kakaoAccount?.profile?.nickname)
                        putExtra("userProfile",user.kakaoAccount?.profile?.profileImageUrl)
                    }
                    startActivity(intent)
                    finish() // 현재 액티비티 종료
                }
            }
        }
    }

    private fun saveLoginInfo(nickname: String?, profileImageUrl: String?){
        val sharedPref = getSharedPreferences("LoginInfo",Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putString("nickname", nickname)
            putString("profileImageUrl",profileImageUrl)
            apply()
        }
    }
}


@Composable
fun LoginScreen(loginAction: ()->Unit){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = "로그인 배경화면", // 접근성을 위한 설명
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // 이미지가 비율을 무시하고 Box를 꽉 채우도록 조정
        )
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally, // Column 내부 요소들을 가로 방향으로 중앙에 배치
        modifier = Modifier.fillMaxSize()
    ) {

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
            modifier = Modifier
                .size(60.dp)
                .clickable(onClick = loginAction) //클릭 이벤트
        )
    }
}
