package com.ryudith.ktorbasicusage1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ryudith.ktorbasicusage1.service.ClientHelper
import com.ryudith.ktorbasicusage1.service.ListMovieEntity
import com.ryudith.ktorbasicusage1.ui.theme.KtorBasicUsage1Theme
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtorBasicUsage1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")

                    LaunchedEffect(key1 = Unit) {
//                        val client = ClientHelper.createInstance()
//
//                        // string response
//                        val stringResponse = client.sendGetRequest()
//                        Log.d("DEBUG_DATA", "string response: ${stringResponse}")
//
//                        // convert string response
//                        val convertResponse = Json.decodeFromString<ListMovieEntity>(stringResponse!!)
//                        Log.d("DEBUG_DATA", "convert response: ${convertResponse}")
//
//                        // object response
//                        val objectResponse = client.getListMovie(1, 10)
//                        Log.d("DEBUG_DATA", "object response: ${objectResponse}")
//
//                        // custom header
//                        val customHeader = client.sendCustomHeader()
//                        Log.d("DEBUG_DATA", "custom header: ${customHeader}")
                    }


                    val coroutineScope = rememberCoroutineScope()
                    val client = ClientHelper.createInstance(packageName, getPreferences(MODE_PRIVATE))

                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            coroutineScope.launch {
                                val cookieResponse = client.sendGetRequest()
                                Log.d("DEBUG_DATA", "cookie response: ${cookieResponse}")
                            }
                        }) {
                            Text(text = "Run Request")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KtorBasicUsage1Theme {
        Greeting("Android")
    }
}