package com.example.nammapustaka_library.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StudentDashboard(
    studentName: String = "",
    onLogout: () -> Unit = {}
) {

    var screen by remember {
        mutableStateOf("dashboard")
    }

    when (screen) {

        "catalog" -> {

            BookCatalogScreen(

                studentName = studentName,

                onBack = {
                    screen = "dashboard"
                }
            )
            return
        }

        "history" -> {

            StudentHistoryScreen(

                studentName = studentName,

                onBack = {
                    screen = "dashboard"
                }
            )
            return
        }
        "review" -> {

            ReviewScreen(

                book = com.example.nammapustaka_library.model.Book(
                    id = 1,
                    name = "Sample Book",
                    author = "Author",
                    category = "Story"
                ),

                studentName = studentName,

                onBack = {
                    screen = "dashboard"
                }
            )

            return
        }
        "reserved" -> {

            ReservedBooksScreen(

                studentId = studentName,

                onBack = {
                    screen = "dashboard"
                }
            )

            return
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center
    ) {

        Text(
            text =
                "👨‍🎓 Welcome $studentName",

            style =
                MaterialTheme.typography
                    .headlineMedium
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Button(
            onClick = {
                screen = "catalog"
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape =
                RoundedCornerShape(16.dp)

        ) {

            Text("Browse Books")
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Button(
            onClick = {
                screen = "review"
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape =
                RoundedCornerShape(16.dp)

        ) {

            Text("Review Corner")
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Button(
            onClick = {
                screen = "history"
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape =
                RoundedCornerShape(16.dp)

        ) {

            Text("My Borrow History")
        }
        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Button(

            onClick = {
                screen = "reserved"
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape =
                RoundedCornerShape(16.dp)

        ) {

            Text("Reserved Books")
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Button(
            onClick = {
                onLogout()
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape =
                RoundedCornerShape(16.dp)

        ) {

            Text("Logout")
        }
    }
}