package com.example.app2.data

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface CourseDao {

    @Upsert
    suspend fun upsertCourse(course:Course)
}