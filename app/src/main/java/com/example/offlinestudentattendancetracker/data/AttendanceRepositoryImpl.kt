package com.example.offlinestudentattendancetracker.data

import com.example.offlinestudentattendancetracker.data.room.*
import com.example.offlinestudentattendancetracker.domain.AttendanceRepository

class AttendanceRepositoryImpl(db: AppDatabase) : AttendanceRepository {

    private val studentDao: StudentDao = db.studentDao()
    private val attendanceDao: AttendanceDao = db.attendanceDao()

    override suspend fun addStudent(student: StudentEntity) {
        studentDao.insert(StudentEntity(
            id = 0,
            name = student.name,
            studentNumber = student.studentNumber
        ))
    }

    override suspend fun getStudents(): List<StudentEntity> {
        return studentDao.getAllStudents().map {
            StudentEntity(it.id, it.name, it.studentNumber)
        }
    }

    override suspend fun markAttendance(attendance: AttendanceEntity) {
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
