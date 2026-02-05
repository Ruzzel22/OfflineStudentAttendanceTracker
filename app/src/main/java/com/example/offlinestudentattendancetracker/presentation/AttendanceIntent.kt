package com.example.offlinestudentattendancetracker.presentation


sealed class AttendanceIntent {
    object Load : AttendanceIntent()
    data class Present(val id: Int) : AttendanceIntent()
    data class Absent(val id: Int) : AttendanceIntent()
}
