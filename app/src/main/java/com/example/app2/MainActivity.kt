package com.example.app2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.app2.data.Course
import com.example.app2.data.CourseDao
import com.example.app2.data.CourseDatabase
import com.example.app2.ui.theme.App2Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
           CourseDatabase::class.java ,
            "notes1.db"
        ).build()
    }
    private val courseDao: CourseDao by lazy {
        database.dao
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App2Theme {
               AddNoteScreen(courseDao = courseDao)

            }
        }
    }
}


@Composable
fun AddNoteScreen(courseDao: CourseDao) {
    val courseCodeState = remember { mutableStateOf("") }
    val descriptionState = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope() // Remember the coroutine scope

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Add New Course", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = courseCodeState.value,
            onValueChange = { courseCodeState.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Course Code") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = descriptionState.value,
            onValueChange = { descriptionState.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Year/Sem") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val title = courseCodeState.value
            val description = descriptionState.value
            if (title.isNotEmpty() && description.isNotEmpty()) {
                val course = Course(
                    courseCode = title,
                    dateSem = description
                )
                // Call upsertNote from within a coroutine scope
                coroutineScope.launch {
                    // Launch a coroutine in the scope
                    courseDao.upsertCourse(course)
                }
            }
        }) {
            Text("Add Course")
        }
    }
}