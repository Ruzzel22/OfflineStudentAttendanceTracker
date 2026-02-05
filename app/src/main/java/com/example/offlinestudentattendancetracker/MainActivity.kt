package com.example.offlinestudentattendancetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.offlinestudentattendancetracker.domain.AttendanceViewModel
import com.example.offlinestudentattendancetracker.domain.AttendanceViewModelFactory
import com.example.offlinestudentattendancetracker.presentation.AttendanceScreen
import com.example.offlinestudentattendancetracker.ui.theme.OfflineStudentAttendanceTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OfflineStudentAttendanceTrackerTheme {
                val viewModel: AttendanceViewModel = viewModel(
                    factory = AttendanceViewModelFactory(application)
                )
                AttendanceScreen(viewModel)
            }
        }
    }
}
