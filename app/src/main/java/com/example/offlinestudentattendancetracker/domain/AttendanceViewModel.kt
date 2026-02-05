package com.example.offlinestudentattendancetracker.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.offlinestudentattendancetracker.data.room.AppDatabase
import com.example.offlinestudentattendancetracker.data.room.AttendanceEntity
import com.example.offlinestudentattendancetracker.presentation.AttendanceIntent
import com.example.offlinestudentattendancetracker.presentation.AttendanceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AttendanceViewModel(application: Application) :
    AndroidViewModel(application) {

    private val db = AppDatabase.create(application)

    private val _state = MutableStateFlow(AttendanceState())
    val state: StateFlow<AttendanceState> = _state

    fun onIntent(intent: AttendanceIntent) {
        viewModelScope.launch {
            when (intent) {
                is AttendanceIntent.Load -> load()

                is AttendanceIntent.Present -> {
                    db.attendanceDao().insert(
                        AttendanceEntity(
                            studentId = intent.id,
                            isPresent = true
                        )
                    )
                }

                is AttendanceIntent.Absent -> {
                    db.attendanceDao().insert(
                        AttendanceEntity(
                            studentId = intent.id,
                            isPresent = false
                        )
                    )
                }
            }
            load()
        }
    }

    private suspend fun load() {
        _state.value = AttendanceState(
            students = db.studentDao().getAllStudents(),
            present = db.attendanceDao().presentCount(),
            absent = db.attendanceDao().absentCount()
        )
    }
}
