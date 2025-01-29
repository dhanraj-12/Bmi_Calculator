package com.example.bmi_calculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bmi_calculator.ui.theme.Bmi_calculatorTheme
import kotlin.jvm.internal.Intrinsics.Kotlin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bmi_calculatorTheme {
                MainScreen()
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

@Composable
fun MainScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Overlay content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InputField()
                }
            }
        }
    )
}

@Composable
fun InputField() {

    val weight  = remember { mutableStateOf("") }
    val height  = remember { mutableStateOf("") }
    var result by remember { mutableStateOf<String?>(null) }
    var category by remember { mutableStateOf<String?>(null) }

    Box( modifier = Modifier
        .fillMaxSize(),
//        .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            ImageFromDrawable(/)
            TextField(
                value = weight.value,
                onValueChange = {
                        newtext -> weight.value  = newtext
                },
                label = { Text("Enter weight") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = height.value,
                onValueChange = {
                        newtext -> height.value  = newtext
                },
                label = { Text("Enter height") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                 result = submit(weight.value, height.value)
                category = FindCategory(result ?: "")
            }) {
                Text("Calculate BMI")
            }

            Spacer(modifier = Modifier.height(16.dp))

//            Text("Your BMI is ${result}")
            if (result != null && category != null) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                        .fillMaxWidth(0.8f)
                        .background(Color(0xFFEEEEEE))
                        .padding(16.dp)
                ) {
                    Column {
                        Text("Your BMI: ${result ?: "N/A"}", color = Color.Black)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Your Category: ${category ?: "N/A"}", color = Color.Black)
                    }
                }
            }
        }
    }
}

//@Composable
@SuppressLint("DefaultLocale")
fun submit(weight : String, height : String) : String {
    return  try {
        val a = weight.toDoubleOrNull()
        val b = height.toDoubleOrNull()
        if(a != null && b != null && b > 0) {
            val bmi = (a*10000)/(b*b);
            String.format("%.2f",bmi)
        } else {
            "Invalid input"
        }
    } catch (e : Exception) {
        "Error: ${e.message}"
    }
}

fun FindCategory(result: String): String {
    val a = result.toDoubleOrNull()
    if (a == null) {
        return "Invalid input"
    } else if (a < 18.5) {
        return "Underweight"
    } else if (a < 25) {
        return "Normal"
    } else if (a < 30) {
        return "Overweight"
    } else {
        return "Obese"
    }
}


@Composable
fun ImageFromDrawable() {
    Image(
        painter = painterResource(id = R.drawable.image),
        contentDescription = "Description of Image",
        modifier = Modifier.size(200.dp)
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Bmi_calculatorTheme {
        Greeting("Android")
        InputField()
    }
}