package com.example.nammapustaka_library.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammapustaka_library.data.DatabaseProvider
import com.example.nammapustaka_library.data.ReviewRepository
import com.example.nammapustaka_library.model.Book
import com.example.nammapustaka_library.model.Review
import com.example.nammapustaka_library.viewmodel.ReviewViewModel
import com.example.nammapustaka_library.viewmodel.ReviewViewModelFactory

@Composable
fun ReviewScreen(

    book: Book,

    studentName: String,

    onBack: () -> Unit = {}
) {

    BackHandler {
        onBack()
    }

    val context =
        LocalContext.current

    val repository = remember {

        ReviewRepository(

            DatabaseProvider
                .getDatabase(context)
                .reviewDao()
        )
    }

    val viewModel: ReviewViewModel =
        viewModel(

            factory = ReviewViewModelFactory(
                repository,
                book.id
            )
        )

    val reviews by
    viewModel.reviews
        .collectAsState()

    var reviewText by remember {
        mutableStateOf("")
    }

    var selectedStars by remember {
        mutableStateOf(5)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text =
                "⭐ Reviews - ${book.name}",

            style =
                MaterialTheme.typography
                    .headlineSmall
        )

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )

        Row {

            (1..5).forEach { star ->

                FilterChip(

                    selected =
                        selectedStars == star,

                    onClick = {
                        selectedStars = star
                    },

                    label = {
                        Text("⭐ $star")
                    }
                )

                Spacer(
                    modifier =
                        Modifier.width(8.dp)
                )
            }
        }

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )

        OutlinedTextField(

            value = reviewText,

            onValueChange = {
                reviewText = it
            },

            label = {
                Text("Write Review")
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
                    reviewText.isNotBlank()
                ) {

                    viewModel.addReview(

                        Review(

                            bookId = book.id,

                            studentName =
                                studentName,

                            rating =
                                selectedStars,

                            reviewText =
                                reviewText
                        )
                    )

                    Toast.makeText(

                        context,

                        "Review Submitted",

                        Toast.LENGTH_SHORT

                    ).show()

                    reviewText = ""
                    selectedStars = 5
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape =
                RoundedCornerShape(16.dp)

        ) {

            Text("Save Review")
        }

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        LazyColumn {

            if (reviews.isEmpty()) {

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
                                "📭 No reviews yet"
                        )
                    }
                }
            }

            items(reviews) { review ->

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
                                "👤 ${review.studentName}"
                        )

                        Spacer(
                            modifier =
                                Modifier.height(6.dp)
                        )

                        Text(
                            text =
                                "⭐".repeat(review.rating)
                        )

                        Spacer(
                            modifier =
                                Modifier.height(6.dp)
                        )

                        Text(
                            text =
                                review.reviewText
                        )
                    }
                }
            }
        }
    }
}