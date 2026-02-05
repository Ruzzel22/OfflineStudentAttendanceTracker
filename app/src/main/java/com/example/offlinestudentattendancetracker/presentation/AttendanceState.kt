package com.example.offlinestudentattendancetracker.presentation

import com.example.offlinestudentattendancetracker.data.room.StudentEntity

data class AttendanceState(
    val students: List<StudentEntity> = emptyList(),
    val present: Int = 0,
    val absent: Int = 0
)
