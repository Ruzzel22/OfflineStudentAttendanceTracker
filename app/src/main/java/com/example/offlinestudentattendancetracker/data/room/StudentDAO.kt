package com.example.offlinestudentattendancetracker.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.offlinestudentattendancetracker.domain.Student

@Dao
interface StudentDao {

    @Query("SELECT * FROM students")
    suspend fun getAllStudents(): List<Student>

    @Insert
    suspend fun insert(student: StudentEntity)
}
