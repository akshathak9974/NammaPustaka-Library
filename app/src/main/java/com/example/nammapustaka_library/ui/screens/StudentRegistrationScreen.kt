package com.example.nammapustaka_library.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
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
import com.example.nammapustaka_library.model.Student
@Composable
fun StudentRegistrationScreen(
    onBack: () -> Unit = {}
) {

    BackHandler {
        onBack()
    }

    val context = LocalContext.current

    val repository = remember {

        StudentRepository(
            DatabaseProvider
                .getDatabase(context)
                .studentDao()
        )
    }

    val viewModel: StudentViewModel =
        viewModel(
            factory =
                StudentViewModelFactory(
                    repository
                )
        )

    val students by
    viewModel.students.collectAsState(
        initial = emptyList()
    )

    var name by remember {
        mutableStateOf("")
    }

    var studentId by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var studentClass by remember {
        mutableStateOf("")
    }

    val snackbarHostState =
        remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    Scaffold(

        snackbarHost = {

            SnackbarHost(
                hostState =
                    snackbarHostState
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text =
                    "👨‍🎓 Student Registration",

                style =
                    MaterialTheme.typography.headlineSmall
            )

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )

            OutlinedTextField(
                value = name,

                onValueChange = {
                    name = it
                },

                label = {
                    Text("Student Name")
                },

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(16.dp)
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = studentId,

                onValueChange = {
                    studentId = it
                },

                label = {
                    Text("Student ID")
                },

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(16.dp)
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = password,

                onValueChange = {
                    password = it
                },

                label = {
                    Text("Password")
                },

                visualTransformation =
                    PasswordVisualTransformation(),

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(16.dp)
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = studentClass,

                onValueChange = {
                    studentClass = it
                },

                label = {
                    Text("Class")
                },

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(16.dp)
            )

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )

            Button(
                onClick = {

                    if (
                        name.isNotBlank() &&
                        studentId.isNotBlank() &&
                        password.isNotBlank() &&
                        studentClass.isNotBlank()
                    ) {

                        viewModel.registerStudent(

                            Student(
                                studentId = studentId,
                                studentName = name,
                                password = password,
                                studentClass = studentClass
                            )
                        )

                        scope.launch {

                            snackbarHostState
                                .showSnackbar(
                                    "✅ Student Registered"
                                )
                        }

                        name = ""
                        studentId = ""
                        password = ""
                        studentClass = ""
                    }
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
                modifier =
                    Modifier.height(24.dp)
            )

            Text(
                text =
                    "📋 Registered Students",

                style =
                    MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            LazyColumn {

                items(students) { student ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),

                        shape =
                            RoundedCornerShape(20.dp),

                        elevation =
                            CardDefaults.cardElevation(
                                defaultElevation = 8.dp
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(16.dp)
                        ) {

                            Text(
                                text =
                                    "👤 ${student.studentName}"
                            )

                            Text(
                                text =
                                    "🆔 ${student.studentId}"
                            )

                            Text(
                                text =
                                    "🏫 ${student.studentClass}"
                            )
                        }
                    }
                }
            }
        }
    }
}