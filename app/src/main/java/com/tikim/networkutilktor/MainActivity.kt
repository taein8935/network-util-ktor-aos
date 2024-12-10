package com.tikim.networkutilktor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tikim.networkutilktor.animal.data.repository.AnimalRepositoryImpl
import com.tikim.networkutilktor.animal.presentation.AnimalScreen
import com.tikim.networkutilktor.animal.presentation.AnimalViewModel
import com.tikim.networkutilktor.core.data.HttpClientFactory
import com.tikim.networkutilktor.ui.theme.NetworkutilktorTheme
import io.ktor.client.engine.okhttp.OkHttp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModels<AnimalViewModel> {
            viewModelFactory {
                initializer {
                    AnimalViewModel(
                        animalRepository = AnimalRepositoryImpl(
                            httpClient =  HttpClientFactory.create(OkHttp.create())
                        )
                    )
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            NetworkutilktorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AnimalScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NetworkutilktorTheme {
        Greeting("Android")
    }
}