package com.example.offlinestudentattendancetracker.data

import com.example.offlinestudentattendancetracker.data.room.AttendanceDao
import com.example.offlinestudentattendancetracker.data.room.AppDatabase
import com.example.offlinestudentattendancetracker.data.room.StudentDao
import com.example.offlinestudentattendancetracker.data.room.AttendanceEntity
import com.example.offlinestudentattendancetracker.data.room.StudentEntity
import com.example.offlinestudentattendancetracker.domain.Attendance
import com.example.offlinestudentattendancetracker.domain.AttendanceRepository
import com.example.offlinestudentattendancetracker.domain.Student

class AttendanceRepositoryImpl(db: AppDatabase) : AttendanceRepository {

    private val studentDao: StudentDao = db.studentDao()
    private val attendanceDao: AttendanceDao = db.attendanceDao()

    override suspend fun addStudent(student: Student) {
        studentDao.insert(StudentEntity(
            id = 0, // autoGenerate = true
            name = student.name,
            studentNumber = student.studentNumber
        ))
    }

    override suspend fun getStudents(): List<Student> {
        return studentDao.getAllStudents().map {
            Student(it.id, it.name, it.studentNumber)
        }
    }

    override suspend fun markAttendance(attendance: Attendance) {
        attendanceDao.insert(
            AttendanceEntity(
                studentId = attendance.studentId,
                isPresent = attendance.isPresent
            )
        )
    }

    override suspend fun getSummary(): Pair<Int, Int> {
        val present = attendanceDao.presentCount()
        val absent = attendanceDao.absentCount()
        return Pair(present, absent)
    }
}
