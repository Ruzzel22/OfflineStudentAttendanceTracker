package com.example.offlinestudentattendancetracker.presentation

import com.example.offlinestudentattendancetracker.domain.Student

data class AttendanceState(
    val students: List<Student> = emptyList(),
    val present: Int = 0,
    val absent: Int = 0
)
