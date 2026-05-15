package com.example.nammapustaka_library.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammapustaka_library.data.BookRepository
import com.example.nammapustaka_library.data.DatabaseProvider
import com.example.nammapustaka_library.viewmodel.BookViewModel
import com.example.nammapustaka_library.viewmodel.BookViewModelFactory

@Composable
fun LeaderboardScreen(

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

    val leaderboard by
    viewModel.leaderboard
        .collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text =
                "🏆 Reading Leaderboard",

            style =
                MaterialTheme.typography
                    .headlineSmall
        )

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )

        if (leaderboard.isEmpty()) {

            Text(
                text =
                    "No leaderboard data yet"
            )
        }

        LazyColumn {

            itemsIndexed(
                leaderboard
            ) { index, item ->

                val medal = when(index) {

                    0 -> "🥇"
                    1 -> "🥈"
                    2 -> "🥉"

                    else -> "📚"
                }

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
                                "$medal ${item.studentName}",

                            style =
                                MaterialTheme.typography
                                    .titleMedium
                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        Text(
                            text =
                                "Books Read: ${item.totalBooks}"
                        )
                    }
                }
            }
        }
    }
}