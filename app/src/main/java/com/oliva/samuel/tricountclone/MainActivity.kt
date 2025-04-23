package com.oliva.samuel.tricountclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.ui.theme.TriCountCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TriCountCloneApp {
                Text(
                    text = "Hello world!",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun TriCountCloneApp(content: @Composable () -> Unit) {
    TriCountCloneTheme {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TriCountCloneTheme {
        Text("Android")
    }
}
