package com.example.offlinestudentattendancetracker.domain

import com.example.offlinestudentattendancetracker.data.room.AttendanceEntity
import com.example.offlinestudentattendancetracker.data.room.StudentEntity

interface AttendanceRepository {
    suspend fun addStudent(student: StudentEntity)
    suspend fun getStudents(): List<StudentEntity>
    suspend fun markAttendance(attendance: AttendanceEntity)
    suspend fun getSummary(): Pair<Int, Int>
}
