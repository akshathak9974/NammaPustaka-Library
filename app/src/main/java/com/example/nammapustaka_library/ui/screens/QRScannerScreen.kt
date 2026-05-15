package com.example.nammapustaka_library.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammapustaka_library.data.BookRepository
import com.example.nammapustaka_library.data.DatabaseProvider
import com.example.nammapustaka_library.data.StudentRepository
import com.example.nammapustaka_library.model.Student
import com.example.nammapustaka_library.viewmodel.BookViewModel
import com.example.nammapustaka_library.viewmodel.BookViewModelFactory
import com.example.nammapustaka_library.viewmodel.StudentViewModel
import com.example.nammapustaka_library.viewmodel.StudentViewModelFactory
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@SuppressLint("UnsafeOptInUsageError")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRScannerScreen(
    onBack: () -> Unit = {}
) {

    BackHandler {
        onBack()
    }

    val context = LocalContext.current

    val lifecycleOwner =
        LocalLifecycleOwner.current

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

    val bookViewModel: BookViewModel =
        viewModel(
            factory =
                BookViewModelFactory(
                    bookRepository
                )
        )

    val studentViewModel: StudentViewModel =
        viewModel(
            factory =
                StudentViewModelFactory(
                    studentRepository
                )
        )

    val students by
    studentViewModel.students
        .collectAsState()

    var scannedBookIdState
            by remember {

                mutableStateOf<Int?>(null)
            }

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedStudent
            by remember {

                mutableStateOf<Student?>(null)
            }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        Text(
            text = "📷 Scan Book QR",

            style =
                MaterialTheme.typography
                    .headlineSmall
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        AndroidView(

            factory = { ctx ->

                val previewView =
                    PreviewView(ctx)

                previewView.implementationMode =
                    PreviewView
                        .ImplementationMode
                        .COMPATIBLE

                val cameraProviderFuture =
                    ProcessCameraProvider
                        .getInstance(ctx)

                cameraProviderFuture
                    .addListener({

                        val cameraProvider =
                            cameraProviderFuture.get()

                        val preview =
                            Preview.Builder()
                                .build()

                        preview.surfaceProvider =
                            previewView.surfaceProvider

                        val barcodeScanner =
                            BarcodeScanning
                                .getClient()

                        val imageAnalysis =
                            ImageAnalysis.Builder()
                                .build()

                        imageAnalysis
                            .setAnalyzer(

                                Executors
                                    .newSingleThreadExecutor()

                            ) { imageProxy ->

                                processImageProxy(

                                    barcodeScanner,

                                    imageProxy

                                ) { barcodeValue ->

                                    val scannedBookId =
                                        barcodeValue
                                            .toIntOrNull()

                                    if (
                                        scannedBookId != null
                                    ) {

                                        scannedBookIdState =
                                            scannedBookId
                                    }
                                }
                            }

                        try {

                            cameraProvider
                                .unbindAll()

                            cameraProvider
                                .bindToLifecycle(

                                    lifecycleOwner,

                                    CameraSelector
                                        .DEFAULT_BACK_CAMERA,

                                    preview,

                                    imageAnalysis
                                )

                        } catch (e: Exception) {

                            Log.e(
                                "QRScanner",
                                "Camera binding failed",
                                e
                            )
                        }

                    },

                        ContextCompat
                            .getMainExecutor(ctx)
                    )

                previewView
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        scannedBookIdState?.let { bookId ->

            Text(
                text =
                    "✅ Scanned Book ID: $bookId",

                style =
                    MaterialTheme.typography
                        .titleMedium
            )

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            ExposedDropdownMenuBox(

                expanded = expanded,

                onExpandedChange = {

                    expanded = !expanded
                }
            ) {

                OutlinedTextField(

                    value =
                        selectedStudent?.studentName
                            ?: "",

                    onValueChange = {},

                    readOnly = true,

                    label = {
                        Text("Select Student")
                    },

                    trailingIcon = {

                        ExposedDropdownMenuDefaults
                            .TrailingIcon(
                                expanded = expanded
                            )
                    },

                    modifier = Modifier
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

                                Text(
                                    "${student.studentName} (${student.studentId})"
                                )
                            },

                            onClick = {

                                selectedStudent =
                                    student

                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )

            Button(

                onClick = {

                    selectedStudent?.let {

                        bookViewModel.issueBook(

                            bookId = bookId,

                            studentName =
                                it.studentName,

                            studentId =
                                it.studentId
                        )
                    }
                },

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Text("Issue Book")
            }
        }
    }
}

@androidx.camera.core.ExperimentalGetImage
private fun processImageProxy(

    barcodeScanner:
    com.google.mlkit.vision.barcode.BarcodeScanner,

    imageProxy: ImageProxy,

    onBarcodeDetected:
        (String) -> Unit
) {

    val mediaImage =
        imageProxy.image

    if (mediaImage != null) {

        val image =
            InputImage.fromMediaImage(

                mediaImage,

                imageProxy.imageInfo
                    .rotationDegrees
            )

        barcodeScanner
            .process(image)

            .addOnSuccessListener { barcodes ->

                for (barcode in barcodes) {

                    barcode.rawValue?.let {

                        onBarcodeDetected(it)
                    }
                }
            }

            .addOnCompleteListener {

                imageProxy.close()
            }

    } else {

        imageProxy.close()
    }
}