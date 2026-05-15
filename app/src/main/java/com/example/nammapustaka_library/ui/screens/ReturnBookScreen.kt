package com.example.nammapustaka_library.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReturnBookScreen() {

    var studentName by remember { mutableStateOf("") }
    var bookName by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "📚 Return Book",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = studentName,
            onValueChange = { studentName = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Student Name") }
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = bookName,
            onValueChange = { bookName = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Book Name") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if(studentName.isNotEmpty() && bookName.isNotEmpty()) {
                    message = "Book '$bookName' returned by $studentName"

                    studentName = ""
                    bookName = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Return")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(message)
    }
}