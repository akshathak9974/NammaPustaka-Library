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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammapustaka_library.data.DatabaseProvider
import com.example.nammapustaka_library.data.ReservationRepository
import com.example.nammapustaka_library.viewmodel.ReservationViewModel
import com.example.nammapustaka_library.viewmodel.ReservationViewModelFactory

@Composable
fun ReservedBooksScreen(

    studentId: String,

    onBack: () -> Unit = {}
) {

    BackHandler {
        onBack()
    }

    val context =
        LocalContext.current

    val repository = remember {

        ReservationRepository(

            DatabaseProvider
                .getDatabase(context)
                .reservationDao()
        )
    }

    val reservations by
    repository
        .getReservationsForStudent(studentId)
        .collectAsState(
            initial = emptyList()
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "📚 Reserved Books",

            style =
                MaterialTheme.typography
                    .headlineSmall
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        if (reservations.isEmpty()) {

            Text(
                text =
                    "No reserved books yet"
            )
        }

        LazyColumn {

            items(reservations) { reservation ->

                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),

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

                        Text(
                            text =
                                "📘 ${reservation.bookName}"
                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        Text(
                            text =
                                "👨‍🎓 ${reservation.studentName}"
                        )
                    }
                }
            }
        }
    }
}