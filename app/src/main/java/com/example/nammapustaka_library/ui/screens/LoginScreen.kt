package com.example.nammapustaka_library.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammapustaka_library.data.DatabaseProvider
import com.example.nammapustaka_library.data.StudentRepository
import com.example.nammapustaka_library.viewmodel.StudentViewModel
import com.example.nammapustaka_library.viewmodel.StudentViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun LoginScreen() {

    var screen by remember {
        mutableStateOf("login")
    }

    var loggedStudentName by remember {
        mutableStateOf("")
    }

    if (screen == "teacher") {

        TeacherDashboard(

            onLogout = {
                screen = "login"
            }
        )
        return
    }

    if (screen == "student") {

        StudentDashboard(

            studentName =
                loggedStudentName,

            onLogout = {
                screen = "login"
            }
        )
        return
    }

    val context = LocalContext.current

    val repository = remember {

        StudentRepository(

            DatabaseProvider
                .getDatabase(context)
                .studentDao()
        )
    }

    val viewModel: StudentViewModel = viewModel(

        factory =
            StudentViewModelFactory(repository)
    )

    var id by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center
    ) {

        Text(
            text = "📚 Namma Pustaka",

            style =
                MaterialTheme.typography
                    .headlineLarge
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text =
                "Smart Library Assistant"
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        OutlinedTextField(
            value = id,

            onValueChange = {
                id = it
            },

            modifier =
                Modifier.fillMaxWidth(),

            label = {
                Text("User ID")
            },

            shape =
                RoundedCornerShape(16.dp)
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        OutlinedTextField(
            value = password,

            onValueChange = {
                password = it
            },

            modifier =
                Modifier.fillMaxWidth(),

            label = {
                Text("Password")
            },

            visualTransformation =

                if (passwordVisible)
                    androidx.compose.ui.text.input
                        .VisualTransformation.None
                else
                    PasswordVisualTransformation(),

            trailingIcon = {

                TextButton(
                    onClick = {
                        passwordVisible =
                            !passwordVisible
                    }
                ) {

                    Text(

                        if (passwordVisible)
                            "Hide"
                        else
                            "Show"
                    )
                }
            },

            shape =
                RoundedCornerShape(16.dp)
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        if (errorMessage.isNotEmpty()) {

            Text(
                text = errorMessage,

                color =
                    MaterialTheme.colorScheme.error
            )

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )
        }

        Button(

            onClick = {

                if (
                    id == "teacher" &&
                    password == "1234"
                ) {

                    errorMessage = ""
                    screen = "teacher"

                } else {

                    errorMessage =
                        "❌ Invalid Teacher Login"
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape =
                RoundedCornerShape(16.dp)

        ) {

            Text("Teacher Login")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(

            onClick = {

                scope.launch {

                    val student =
                        viewModel.loginStudent(
                            id,
                            password
                        )

                    if (student != null) {

                        loggedStudentName =
                            student.studentName

                        errorMessage = ""

                        screen = "student"

                    } else {

                        errorMessage =
                            "❌ Invalid Student Login"
                    }
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape =
                RoundedCornerShape(16.dp)

        ) {

            Text("Student Login")
        }
    }
}