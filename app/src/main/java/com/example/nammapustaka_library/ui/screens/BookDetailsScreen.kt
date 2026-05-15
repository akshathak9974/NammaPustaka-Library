package com.example.nammapustaka_library.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nammapustaka_library.data.DatabaseProvider
import com.example.nammapustaka_library.data.ReservationRepository
import com.example.nammapustaka_library.model.Book
import com.example.nammapustaka_library.ui.components.QRCodeGenerator
import com.example.nammapustaka_library.viewmodel.ReservationViewModel
import com.example.nammapustaka_library.viewmodel.ReservationViewModelFactory

@Composable
fun BookDetailsScreen(

    book: Book,

    studentName: String,

    onBack: () -> Unit = {}
) {

    BackHandler {
        onBack()
    }

    val context =
        LocalContext.current

    val reservationRepository = remember {

        ReservationRepository(

            DatabaseProvider
                .getDatabase(context)
                .reservationDao()
        )
    }

    val reservationViewModel:
            ReservationViewModel = viewModel(

        factory = ReservationViewModelFactory(
            reservationRepository
        )
    )

    var screen by remember {
        mutableStateOf("details")
    }

    if (screen == "review") {

        ReviewScreen(

            book = book,

            studentName = studentName,

            onBack = {
                screen = "details"
            }
        )

        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(20.dp)
    ) {

        AsyncImage(

            model = book.imageUri,

            contentDescription =
                "Book Cover",

            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),

            contentScale =
                ContentScale.Crop
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "📘 ${book.name}",

            style =
                MaterialTheme.typography
                    .headlineMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text =
                "✍ Author: ${book.author}",

            style =
                MaterialTheme.typography
                    .titleMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text =
                "📚 Category: ${book.category}"
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "📱 Book QR Code",

            style =
                MaterialTheme.typography
                    .titleMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        QRCodeGenerator(
            data = book.id.toString()
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text =
                "📝 Summary",

            style =
                MaterialTheme.typography
                    .titleMedium
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text =
                "ಈ ಪುಸ್ತಕವು ವಿದ್ಯಾರ್ಥಿಗಳಿಗೆ ಜ್ಞಾನ ಮತ್ತು ಓದು ಅಭ್ಯಾಸವನ್ನು ಹೆಚ್ಚಿಸಲು ಸಹಾಯ ಮಾಡುತ್ತದೆ."
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(

            onClick = {

                reservationViewModel.reserveBook(

                    bookId = book.id,

                    bookName = book.name,

                    studentName = studentName,

                    studentId = studentName
                )

                Toast.makeText(

                    context,

                    "Book Reserved",

                    Toast.LENGTH_SHORT

                ).show()
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape =
                RoundedCornerShape(16.dp)

        ) {

            Text("Reserve Book")
        }

        Spacer(
            modifier = Modifier.height(14.dp)
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

            Text("⭐ Review This Book")
        }
    }
}