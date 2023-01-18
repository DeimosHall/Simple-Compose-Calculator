package com.deimos.simplecomposecalculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deimos.simplecomposecalculator.ui.theme.SimpleComposeCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleComposeCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyParent()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleComposeCalculatorTheme {
        MyParent()
    }
}

@Composable
fun MyParent() {
    Column(modifier = Modifier.fillMaxSize()) {
        MyCard()
    }
}

@Composable
fun MyCard() {
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 16.dp,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                SetTextFields()
            }
        }
    }
}

@Composable
fun MyTextField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "Number") },
        placeholder = { Text(text = "Enter a number") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun OperationsList(operation: String, onItemSelected: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = operation == "Add", onClick = { onItemSelected("Add") })
            Text(text = "Add")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = operation == "Subtract",
                onClick = { onItemSelected("Subtract") })
            Text(text = "Subtract")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = operation == "Multiply",
                onClick = { onItemSelected("Multiply") })
            Text(text = "Multiply")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = operation == "Divide", onClick = { onItemSelected("Divide") })
            Text(text = "Divide")
        }
    }
}

@Composable
fun SetTextFields() {
    var operationSelected by rememberSaveable { mutableStateOf("Add") }
    var value1 by rememberSaveable { mutableStateOf("") }
    var value2 by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf(0.0) }
    val number1 = when {
        value1.isEmpty() -> 0.0
        else -> value1.toDouble()
    }
    val number2 = when {
        value2.isEmpty() -> 0.0
        else -> value2.toDouble()
    }

    Column {
        MyTextField(
            value = value1,
            onValueChange = {
                try {
                    it.toDouble()
                    value1 = it
                    Log.d("MYTAG", it)
                } catch (e: Exception) {
                    Log.d("MYTAG", it)
                }
            })
        MySpacer()
        MyTextField(
            value = value2,
            onValueChange = {
                try {
                    it.toDouble()
                    value2 = it
                    Log.d("MYTAG", it)
                } catch (e: Exception) {
                    Log.d("MYTAG", it)
                }
            })
        MySpacer()
        OperationsList(operation = operationSelected, onItemSelected = { operationSelected = it })
        MySpacer()
        Button(onClick = {
            when (operationSelected) {
                "Add" -> result = number1 + number2
                "Subtract" -> result = number1 - number2
                "Multiply" -> result = number1 * number2
                "Divide" -> {
                    try {
                        result = number1 / number2
                    } catch (e: Exception) {
                        // Show a error message
                    }
                }
            }
        },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Calculate")
        }
        MySpacer()
        Text(text = "Result: $result")
    }
}

@Composable
fun MySpacer(height: Int = 15) {
    Spacer(modifier = Modifier.height(height.dp))
}