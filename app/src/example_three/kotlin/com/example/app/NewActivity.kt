package com.example.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat
import com.example.app.external_intents.IntentActivity
import com.example.app.ui.components.CustomSnackbar
import com.example.app.ui.components.TransparentHintTextField
import kotlinx.coroutines.launch

class NewActivity : AppCompatActivity() {


    private lateinit var launchIntentActivityLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(ComposeView(this).apply {
            setContent {
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }
                LaunchedEffect(key1 = Unit) {
                    launchIntentActivityLauncher = activityResultRegistry.register(
                        "INTENT_ACTIVITY_LAUNCHER",
                        ActivityResultContracts.StartActivityForResult()
                    ) { activityResult ->
                        scope.launch {
                            val resultCode = activityResult.resultCode
                            var messageToDisplay = ""
                            if (resultCode == RESULT_OK)
                                messageToDisplay = "Данные обработаны корректно!"
                            else {
                                val resultMessage = activityResult.data?.getStringExtra("message")
                                val code = activityResult.data?.getStringExtra("code")

                                resultMessage?.let { messageToDisplay += "Ошибка! : $resultMessage ." }
                                code?.let { messageToDisplay += " Код: $resultCode" }
                            }

                            snackbarHostState.showSnackbar(
                                message = messageToDisplay,
                                duration = SnackbarDuration.Short
                            )
                        }

                    }

                }
                var action by rememberSaveable {
                    mutableStateOf("")
                }
                var login by rememberSaveable {
                    mutableStateOf("")
                }
                var password by rememberSaveable {
                    mutableStateOf("")
                }
                Scaffold(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                ) { paddingValues ->
                    Column(
                        Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SnackbarHost(
                            hostState = snackbarHostState,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(5.dp)
                        ) { snackbarData ->
                            CustomSnackbar(
                                snackbarData,
                                modifier = Modifier
                                    .padding(10.dp),
                                textColor = Color.White.copy(alpha = 0.8f),
                                surfaceColor = Color.DarkGray.copy(alpha = 0.5f),
                                borderColor = Color.Transparent
                            )
                        }
                        Column(
                            Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(25.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            TransparentHintTextField(
                                hint = "Введите action",
                                onValueChange = {
                                    action = it
                                }
                            )
                            TransparentHintTextField(
                                hint = "Введите login",
                                onValueChange = {
                                    login = it
                                }
                            )
                            TransparentHintTextField(
                                hint = "Введите password",
                                onValueChange = {
                                    password = it
                                }
                            )
                        }
                        Spacer(Modifier.height(5.dp))
                        Button(modifier = Modifier
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally),
                            onClick = {
                                scope.launch {
                                    val newIntent = Intent(
                                        this@NewActivity,
                                        IntentActivity::class.java
                                    )
                                        .apply {
                                            setAction(Intent.ACTION_VIEW)
                                            putExtra(
                                                "action",
                                                action.ifEmpty { null }
                                            )
                                            putExtra(
                                                "login",
                                                login.ifEmpty { null }
                                            )
                                            putExtra(
                                                "password",
                                                password.ifEmpty { null }
                                            )
                                        }
                                    launchIntentActivityLauncher.launch(
                                        newIntent,
                                        ActivityOptionsCompat.makeTaskLaunchBehind()
                                    )
                                }
                            }) {
                            Text(text = "Launch Intent Activity")
                        }
                    }
                }
            }
        })
    }

}