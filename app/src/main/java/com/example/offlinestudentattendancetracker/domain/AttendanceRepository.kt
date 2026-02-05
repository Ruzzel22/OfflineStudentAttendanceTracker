package com.example.offlinestudentattendancetracker.domain

interface AttendanceRepository {
    suspend fun addStudent(student: Student)
    suspend fun getStudents(): List<Student>
    suspend fun markAttendance(attendance: Attendance)
    suspend fun getSummary(): Pair<Int, Int>
}
