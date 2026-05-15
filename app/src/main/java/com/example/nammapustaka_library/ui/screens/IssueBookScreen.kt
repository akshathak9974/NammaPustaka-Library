package com.example.nammapustaka_library.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammapustaka_library.data.BookRepository
import com.example.nammapustaka_library.data.DatabaseProvider
import com.example.nammapustaka_library.data.StudentRepository
import com.example.nammapustaka_library.viewmodel.BookViewModel
import com.example.nammapustaka_library.viewmodel.BookViewModelFactory
import com.example.nammapustaka_library.viewmodel.StudentViewModel
import com.example.nammapustaka_library.viewmodel.StudentViewModelFactory
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueBookScreen(
    onBack: () -> Unit = {}
) {

    BackHandler {
        onBack()
    }

    val context = LocalContext.current

    val bookRepository = remember {

        BookRepository(
            DatabaseProvider
                .getDatabase(context)
                .bookDao()
        )
    }

    val studentRepository = remember {

        StudentRepository(
            DatabaseProvider
                .getDatabase(context)
                .studentDao()
        )
    }

    val bookViewModel: BookViewModel = viewModel(
        factory =
            BookViewModelFactory(bookRepository)
    )

    val studentViewModel: StudentViewModel = viewModel(
        factory =
            StudentViewModelFactory(studentRepository)
    )

    val books by
    bookViewModel.books.collectAsState()

    val students by
    studentViewModel.students.collectAsState()

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedStudentName by remember {
        mutableStateOf("")
    }

    var selectedStudentId by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "📚 Issue Book",

            style =
                MaterialTheme.typography
                    .headlineSmall
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        ExposedDropdownMenuBox(

            expanded = expanded,

            onExpandedChange = {
                expanded = !expanded
            }
        ) {

            OutlinedTextField(

                value = selectedStudentName,

                onValueChange = {},

                readOnly = true,

                label = {
                    Text("Select Student")
                },

                trailingIcon = {
                    ExposedDropdownMenuDefaults
                        .TrailingIcon(expanded)
                },

                modifier =
                    Modifier
                        .menuAnchor()
                        .fillMaxWidth()
            )

            ExposedDropdownMenu(

                expanded = expanded,

                onDismissRequest = {
                    expanded = false
                }
            ) {

                students.forEach { student ->

                    DropdownMenuItem(

                        text = {
                            Text(student.studentName)
                        },

                        onClick = {

                            selectedStudentName =
                                student.studentName

                            selectedStudentId =
                                student.studentId

                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        LazyColumn {

            if (books.isEmpty()) {

                item {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),

                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text(
                            text =
                                "📭 No books added yet"
                        )
                    }
                }
            }

            items(books) { book ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),

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
                                "📘 ${book.name}",

                            style =
                                MaterialTheme.typography
                                    .titleMedium
                        )

                        Spacer(
                            modifier =
                                Modifier.height(6.dp)
                        )

                        Text(
                            text =
                                "✍ ${book.author}"
                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        if (book.isIssued) {

                            Text(
                                text =
                                    "🔴 Status: Issued",

                                color = Color.Red
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(6.dp)
                            )

                            Text(
                                text =
                                    "👤 ${book.issuedTo}"
                            )

                            Text(
                                text =
                                    "📅 ${book.issueDate}"
                            )

                            Text(
                                text =
                                    "⏳ ${book.returnDate}"
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )

                            Button(
                                onClick = {

                                    bookViewModel
                                        .returnBook(book.id)
                                }
                            ) {

                                Text("Return")
                            }

                        } else {

                            Text(
                                text =
                                    "🟢 Status: Available",

                                color =
                                    Color(0xFF2E7D32)
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )

                            Button(

                                onClick = {

                                    if (
                                        selectedStudentName
                                            .isNotBlank()
                                    ) {

                                        bookViewModel.issueBook(

                                            bookId = book.id,

                                            studentName =
                                                selectedStudentName,

                                            studentId =
                                                selectedStudentId
                                        )
                                    }
                                }

                            ) {

                                Text("Issue")
                            }
                        }
                    }
                }
            }
        }
    }
}