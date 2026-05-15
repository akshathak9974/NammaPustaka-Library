package com.example.nammapustaka_library.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammapustaka_library.data.BookRepository
import com.example.nammapustaka_library.data.DatabaseProvider
import com.example.nammapustaka_library.model.Book
import com.example.nammapustaka_library.viewmodel.BookViewModel
import com.example.nammapustaka_library.viewmodel.BookViewModelFactory
import coil.compose.AsyncImage

@Composable
fun BookCatalogScreen(

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
        factory = BookViewModelFactory(repository)
    )

    val books by viewModel.books.collectAsState()

    var search by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("All")
    }

    var selectedBook by remember {
        mutableStateOf<Book?>(null)
    }

    val filteredBooks = books.filter {

        val matchesSearch =

            it.name.contains(search, true) ||

                    it.author.contains(search, true)

        val matchesCategory =

            selectedCategory == "All" ||

                    it.category == selectedCategory

        matchesSearch && matchesCategory
    }

    selectedBook?.let {

        BookDetailsScreen(

            book = it,

            studentName = studentName,

            onBack = {
                selectedBook = null
            }
        )

        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "📚 View Books",

            style =
                MaterialTheme.typography
                    .headlineSmall
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = search,

            onValueChange = {
                search = it
            },

            label = {
                Text("Search Books")
            },

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(16.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceEvenly
        ) {

            listOf(
                "All",
                "Story",
                "Science",
                "History"
            ).forEach { item ->

                FilterChip(

                    selected =
                        selectedCategory == item,

                    onClick = {
                        selectedCategory = item
                    },

                    label = {
                        Text(item)
                    }
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        LazyVerticalGrid(

            columns = GridCells.Fixed(2),

            modifier = Modifier.fillMaxSize(),

            verticalArrangement =
                Arrangement.spacedBy(12.dp),

            horizontalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            items(filteredBooks) { book ->

                Card(

                    onClick = {
                        selectedBook = book
                    },

                    shape =
                        RoundedCornerShape(20.dp),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        )
                ) {

                    Column(
                        modifier =
                            Modifier.padding(16.dp),

                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Card(
                            modifier = Modifier.size(80.dp),

                            shape =
                                RoundedCornerShape(16.dp)
                        ) {

                            Box(
                                modifier =
                                    Modifier.fillMaxSize(),

                                contentAlignment =
                                    Alignment.Center
                            ) {

                                AsyncImage(

                                    model = book.imageUri,

                                    contentDescription = "Book Cover",

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                )
                            }
                        }

                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )

                        Text(
                            text = book.name,

                            style =
                                MaterialTheme.typography
                                    .titleMedium
                        )

                        Spacer(
                            modifier =
                                Modifier.height(6.dp)
                        )

                        Text(
                            text = book.author
                        )

                        Spacer(
                            modifier =
                                Modifier.height(6.dp)
                        )

                        Text(
                            text = book.category
                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        if (book.isIssued) {

                            Text(
                                text = "🔴 Issued"
                            )

                        } else {

                            Text(
                                text = "🟢 Available"
                            )
                        }
                    }
                }
            }
        }
    }
}