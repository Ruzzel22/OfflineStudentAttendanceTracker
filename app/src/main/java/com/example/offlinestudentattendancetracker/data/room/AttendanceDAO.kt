package com.example.offlinestudentattendancetracker.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AttendanceDao {

    @Insert
    suspend fun insert(attendance: AttendanceEntity)

    @Query("SELECT COUNT(*) FROM attendance WHERE isPresent = 1")
    suspend fun presentCount(): Int

    @Query("SELECT COUNT(*) FROM attendance WHERE isPresent = 0")
    suspend fun absentCount(): Int
}
