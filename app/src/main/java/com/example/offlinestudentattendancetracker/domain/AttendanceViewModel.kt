package com.example.offlinestudentattendancetracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.offlinestudentattendancetracker.data.AttendanceRepositoryImpl
import com.example.offlinestudentattendancetracker.data.room.AppDatabase
import com.example.offlinestudentattendancetracker.domain.Attendance
import com.example.offlinestudentattendancetracker.domain.Student
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
            val existing = repository.getStudents()
            if (existing.isEmpty()) {
                repository.addStudent(Student(0, "John Doe", "S001"))
                repository.addStudent(Student(0, "Jane Smith", "S002"))
            }
            load()
        }
    }

    fun onIntent(intent: AttendanceIntent) {
        viewModelScope.launch {
            when (intent) {
                is AttendanceIntent.Load -> load()
                is AttendanceIntent.Present -> repository.markAttendance(Attendance(intent.id, true))
                is AttendanceIntent.Absent -> repository.markAttendance(Attendance(intent.id, false))
            }
            load()
        }
    }

    private suspend fun load() {
        val students = repository.getStudents()
        val (present, absent) = repository.getSummary()
        _state.value = AttendanceState(students, present, absent)
    }
}