package com.example.nammapustaka_library.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
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
fun OverdueScreen(

    onBack: () -> Unit = {}
) {

    BackHandler {
        onBack()
    }

    val context =
        LocalContext.current

    val repository = remember {

        BookRepository(

            DatabaseProvider
                .getDatabase(context)
                .bookDao()
        )
    }

    val viewModel: BookViewModel =
        viewModel(

            factory =
                BookViewModelFactory(
                    repository
                )
        )

    val books by
    viewModel.books
        .collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(

            text = "⏰ Book Status",

            style =
                MaterialTheme.typography
                    .headlineSmall
        )

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )

        LazyColumn {

            items(books) { book ->

                val isOverdue =
                    book.overdueDays > 0

                val statusText =
                    if (isOverdue)
                        "Overdue"
                    else
                        "Returned"

                val statusColor =
                    if (isOverdue)
                        Color.Red
                    else
                        Color.Black

                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),

                    shape =
                        RoundedCornerShape(16.dp),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                ) {

                    Column(
                        modifier =
                            Modifier.padding(16.dp)
                    ) {

                        Text(

                            text =
                                "${book.name} - $statusText",

                            color = statusColor
                        )

                        if (isOverdue) {

                            Spacer(
                                modifier =
                                    Modifier.height(6.dp)
                            )

                            Text(

                                text =
                                    "Fine: ₹${book.fine}",

                                color = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}