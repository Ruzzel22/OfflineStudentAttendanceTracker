package com.example.offlinestudentattendancetracker.domain

data class Student(val id: Int, val name: String, val studentNumber: String)
data class Attendance(val studentId: Int, val isPresent: Boolean)
