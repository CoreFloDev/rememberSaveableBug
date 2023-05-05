package com.example.remembersavebug

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.TextFieldValue
import com.example.remembersavebug.ui.theme.RememberSaveBugTheme
import kotlinx.coroutines.flow.flowOf

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RememberSaveBugTheme {
                val state = flowOf("test").collectAsState(initial = null)

                val testMovableContent = remember {
                    movableContentOf {
                        Greeting(state.value ?: "init")
                    }
                }

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    when (LocalConfiguration.current.orientation) {
                        Configuration.ORIENTATION_PORTRAIT -> {
                            Column {
                                Text(text = "column")
                                testMovableContent()
                            }
                        }

                        else -> {
                            Row {
                                testMovableContent()
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    key(name) {
        println("coucou $name")
        var myField by rememberSaveable(stateSaver = TextFieldValue.Saver, key = "myField") {
            mutableStateOf(TextFieldValue("name"))
        }
        TextField(
            value = myField,
            onValueChange = { myField = it },
            modifier = modifier
        )
    }
}
