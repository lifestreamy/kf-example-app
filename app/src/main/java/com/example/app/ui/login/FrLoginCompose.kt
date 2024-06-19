package com.example.app.ui.login

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.app.R
import com.example.app.compose.CustomOutlinedTextField
import com.example.app.compose.RippleCustomTheme
import com.example.app.utils.Globals.currentFragment
import com.example.app.utils.Globals.intentActivity
import com.example.app.utils.Globals.isIntentInitialized
import com.example.app.utils.Globals.isMainInitialized
import com.example.app.utils.Globals.mainActivity
import com.example.app.utils.launchSafe

class FrLoginCompose : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        retainInstance = true
        currentFragment = this
        return ComposeView(requireContext()).apply {
            setContent {
                FrLoginContent()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isMainInitialized() && Build.VERSION.SDK_INT == 23 && (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) ){
            ActivityCompat.requestPermissions(mainActivity, arrayOf(Manifest.permission.READ_PHONE_STATE), 2)
        }
        if (isIntentInitialized() && Build.VERSION.SDK_INT == 23 && (ActivityCompat.checkSelfPermission(intentActivity, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) ){
            ActivityCompat.requestPermissions(intentActivity, arrayOf(Manifest.permission.READ_PHONE_STATE), 2)
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun FrLoginContent() {
        val fontLatoBold = FontFamily(
            Font(R.font.lato_bold, FontWeight.Bold)
        )
        val fontLato = FontFamily(
            Font(R.font.lato)
        )

        val scrollState = rememberScrollState()

        val loginText = remember { mutableStateOf("") }
        val passwordText = remember { mutableStateOf("") }
        val isButtonEnabled = remember(loginText.value, passwordText.value) {
            loginText.value.isNotBlank() && passwordText.value.isNotBlank()
        }

        MaterialTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.background))
                    .padding(16.dp)
                    .verticalScroll(state = scrollState, enabled = true)
            ) {
                Text(
                    fontFamily = fontLatoBold,
                    fontSize = 24.sp,
                    text = "Авторизация",
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = colorResource(id = R.color.text)
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedTextField(
                    value = loginText.value,
                    onValueChange = { loginText.value = it },
                    label = "Логин",
                    placeholder = "Ваш логин",
                    font = fontLato,
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedTextField(
                    value = passwordText.value,
                    onValueChange = { passwordText.value = it },
                    label = "Пароль",
                    placeholder = "Ваш пароль",
                    font = fontLato,
                    isPassword = true
                )

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 72.dp)
                ) {
                    CompositionLocalProvider(LocalRippleTheme provides RippleCustomTheme) {
                        Button(
                            onClick = { handleButtonLogin(loginText.value, passwordText.value) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary), disabledContainerColor = colorResource(
                                id = R.color.primary_disabled
                            )),
                            shape = RoundedCornerShape(16.dp),
                            enabled = isButtonEnabled,
                            ) {
                            Text(text = "Войти", fontFamily = fontLato, color = Color.White, fontSize = 16.sp)
                        }
                    }
                }
            }
        }

    }

    private fun handleButtonLogin(login: String, password: String) {
        launchSafe("on_login") {

        }
    }
}
