package com.example.offlinestudentattendancetracker.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.offlinestudentattendancetracker.data.AttendanceRepositoryImpl
import com.example.offlinestudentattendancetracker.data.room.AppDatabase
import com.example.offlinestudentattendancetracker.data.room.AttendanceEntity
import com.example.offlinestudentattendancetracker.data.room.StudentEntity
import com.example.offlinestudentattendancetracker.presentation.AttendanceIntent
import com.example.offlinestudentattendancetracker.presentation.AttendanceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AttendanceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AttendanceRepositoryImpl(AppDatabase.getInstance(application))
    private val _state = MutableStateFlow(AttendanceState())
    val state: StateFlow<AttendanceState> = _state

    init {
        viewModelScope.launch {
            try {
                val existing = repository.getStudents()
                if (existing.isEmpty()) {
                    repository.addStudent(StudentEntity(name = "John Doe", studentNumber = "S001"))
                    repository.addStudent(StudentEntity(name = "Jane Smith", studentNumber = "S002"))
                }
                load()
            } catch (e: Exception) {
                e.printStackTrace() // log DB exceptions
            }
        }
    }

    fun onIntent(intent: AttendanceIntent) {
        viewModelScope.launch {
            try {
                when (intent) {
                    is AttendanceIntent.Load -> load()
                    is AttendanceIntent.Present -> repository.markAttendance(
                        AttendanceEntity(studentId = intent.id, isPresent = true)
                    )
                    is AttendanceIntent.Absent -> repository.markAttendance(
                        AttendanceEntity(studentId = intent.id, isPresent = false)
                    )
                }
                load()
            } catch (e: Exception) {
                e.printStackTrace() // log DB exceptions
            }
        }
    }

    private suspend fun load() {
        val students = repository.getStudents()
        val (present, absent) = repository.getSummary()
        _state.value = AttendanceState(students, present, absent)
    }
}
