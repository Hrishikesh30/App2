package com.example.app2.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Course::class],
    version = 1
)
abstract class CourseDatabase: RoomDatabase(){
    abstract val dao: CourseDao
}