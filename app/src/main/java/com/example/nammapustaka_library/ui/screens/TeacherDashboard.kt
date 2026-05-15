package com.example.nammapustaka_library.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDashboard(
    onLogout: () -> Unit = {}
) {

    var screen by remember {
        mutableStateOf("dashboard")
    }

    BackHandler(
        enabled = screen != "dashboard"
    ) {
        screen = "dashboard"
    }

    when (screen) {

        "addbook" -> {

            AddBookScreen(
                onBack = {
                    screen = "dashboard"
                }
            )
            return
        }

        "manage" -> {

            ManageBooksScreen(
                onBack = {
                    screen = "dashboard"
                }
            )
            return
        }

        "issue" -> {

            IssueBookScreen(
                onBack = {
                    screen = "dashboard"
                }
            )
            return
        }

        "leaderboard" -> {

            LeaderboardScreen(
                onBack = {
                    screen = "dashboard"
                }
            )
            return
        }

        "qr" -> {

            QRScannerScreen(
                onBack = {
                    screen = "dashboard"
                }
            )
            return
        }

        "overdue" -> {

            OverdueScreen(

                onBack = {
                    screen = "dashboard"
                }
            )

            return
        }

        "student" -> {

            StudentRegistrationScreen(
                onBack = {
                    screen = "dashboard"
                }
            )
            return
        }
        "leaderboard" -> {

            LeaderboardScreen(

                onBack = {
                    screen = "dashboard"
                }
            )

            return
        }
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text = "📚 Namma Pustaka"
                        )

                        Text(
                            text =
                                "Smart Library Assistant",

                            style =
                                MaterialTheme.typography
                                    .bodySmall
                        )
                    }
                }
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(
                text = "👩‍🏫 Teacher Dashboard",

                style =
                    MaterialTheme.typography
                        .headlineMedium
            )

            Spacer(
                modifier = Modifier.height(24.dp)
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

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    screen = "student"
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape =
                    RoundedCornerShape(16.dp)

            ) {

                Text("Register Student")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    screen = "addbook"
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape =
                    RoundedCornerShape(16.dp)

            ) {

                Text("Add New Book")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    screen = "manage"
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape =
                    RoundedCornerShape(16.dp)

            ) {

                Text("Manage Books")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    screen = "issue"
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape =
                    RoundedCornerShape(16.dp)

            ) {

                Text("Issue Book")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    screen = "leaderboard"
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape =
                    RoundedCornerShape(16.dp)

            ) {

                Text("Reading Leaderboard")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    screen = "qr"
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape =
                    RoundedCornerShape(16.dp)

            ) {

                Text("QR Borrow")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    screen = "overdue"
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape =
                    RoundedCornerShape(16.dp)

            ) {

                Text("Overdue Status")
            }

        }
    }
}