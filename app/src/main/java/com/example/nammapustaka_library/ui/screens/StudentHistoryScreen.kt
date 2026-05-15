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
import com.example.nammapustaka_library.viewmodel.BookViewModel
import com.example.nammapustaka_library.viewmodel.BookViewModelFactory

@Composable
fun StudentHistoryScreen(

    studentName: String,

    onBack: () -> Unit = {}
) {

    BackHandler {
        onBack()
    }

    val context = LocalContext.current

    val repository = remember {

        BookRepository(
            DatabaseProvider
                .getDatabase(context)
                .bookDao()
        )
    }

    val viewModel: BookViewModel = viewModel(

        factory =
            BookViewModelFactory(repository)
    )

    val books by
    viewModel.books.collectAsState()

    val myBooks = books.filter {

        it.issuedTo == studentName
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "📚 My Borrow History",

            style =
                MaterialTheme.typography
                    .headlineSmall
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LazyColumn {

            if (myBooks.isEmpty()) {

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
                                "📭 No borrowed books"
                        )
                    }
                }
            }

            items(myBooks) { book ->

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

                        Text(
                            text =
                                "👤 Issued To: ${book.issuedTo}"
                        )

                        Text(
                            text =
                                "📅 Issue Date: ${book.issueDate}"
                        )

                        Text(
                            text =
                                "⏳ Return Date: ${book.returnDate}"
                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        if (book.fine > 0) {

                            Text(
                                text =
                                    "🔴 Fine: ₹${book.fine}",

                                color = Color.Red
                            )

                        } else {

                            Text(
                                text =
                                    "🟢 No Fine",

                                color =
                                    Color(0xFF2E7D32)
                            )
                        }
                    }
                }
            }
        }
    }
}