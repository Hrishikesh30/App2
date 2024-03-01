package com.example.app2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Course(

    @PrimaryKey
    val courseCode: String,
    val dateSem: String,

)