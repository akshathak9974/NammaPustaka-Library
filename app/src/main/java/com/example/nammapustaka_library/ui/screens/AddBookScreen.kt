package com.example.nammapustaka_library.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nammapustaka_library.data.BookRepository
import com.example.nammapustaka_library.data.DatabaseProvider
import com.example.nammapustaka_library.viewmodel.BookViewModel
import com.example.nammapustaka_library.viewmodel.BookViewModelFactory
import android.widget.Toast

@Composable
fun AddBookScreen(
    onBack: () -> Unit = {}
) {

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

    var bookName by remember {
        mutableStateOf("")
    }

    var author by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf("Story")
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(

            contract =
                ActivityResultContracts.OpenDocument()

        ) { uri ->

            uri?.let {

                context.contentResolver
                    .takePersistableUriPermission(

                        it,

                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )

                imageUri = it
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "📚 Add New Book",

            style =
                MaterialTheme.typography
                    .headlineSmall
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = bookName,

            onValueChange = {
                bookName = it
            },

            label = {
                Text("Book Name")
            },

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = author,

            onValueChange = {
                author = it
            },

            label = {
                Text("Author")
            },

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text("Select Category")

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row {

            listOf(
                "Story",
                "Science",
                "History"
            ).forEach {

                FilterChip(

                    selected =
                        category == it,

                    onClick = {
                        category = it
                    },

                    label = {
                        Text(it)
                    }
                )

                Spacer(
                    modifier =
                        Modifier.width(8.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(

            onClick = {

                imagePickerLauncher.launch(
                    arrayOf("image/*")
                )
            },

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text("📷 Capture Book Image")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        imageUri?.let {

            AsyncImage(

                model = it,

                contentDescription =
                    "Book Image",

                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),

                contentScale =
                    ContentScale.Crop
            )

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )
        }

        Button(

            onClick = {

                if (

                    bookName.isNotBlank() &&
                    author.isNotBlank()

                ) {

                    viewModel.addBook(

                        name = bookName,

                        author = author,

                        category = category,

                        imageUri =
                            imageUri?.toString()
                                ?: ""
                    )

                    Toast.makeText(

                        context,

                        "Book Added Successfully",

                        Toast.LENGTH_SHORT

                    ).show()

                    bookName = ""
                    author = ""
                    category = "Story"
                    imageUri = null
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape =
                RoundedCornerShape(16.dp)

        ) {

            Text("Save Book")
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "📚 Added Books",

            style =
                MaterialTheme.typography
                    .titleMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        LazyColumn {

            items(books) { book ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),

                    shape =
                        RoundedCornerShape(20.dp),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        )
                ) {

                    Column(
                        modifier =
                            Modifier.padding(16.dp)
                    ) {

                        AsyncImage(

                            model = book.imageUri,

                            contentDescription =
                                "Book Cover",

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),

                            contentScale =
                                ContentScale.Crop
                        )

                        Spacer(
                            modifier =
                                Modifier.height(10.dp)
                        )

                        Text("📘 ${book.name}")

                        Spacer(
                            modifier =
                                Modifier.height(6.dp)
                        )

                        Text("✍ Author: ${book.author}")

                        Spacer(
                            modifier =
                                Modifier.height(6.dp)
                        )

                        Text("📚 Category: ${book.category}")
                    }
                }
            }
        }
    }
}