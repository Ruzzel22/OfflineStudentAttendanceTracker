package com.example.offlinestudentattendancetracker.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.offlinestudentattendancetracker.domain.AttendanceViewModel

@Composable
fun AttendanceScreen(viewModel: AttendanceViewModel) {

    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Present: ${state.present}")
        Text("Absent: ${state.absent}")
        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(state.students, key = { it.id }) { student ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(student.name)
                    Row {
                        Button(onClick = { viewModel.onIntent(AttendanceIntent.Present(student.id)) }) {
                            Text("Present")
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = { viewModel.onIntent(AttendanceIntent.Absent(student.id)) }) {
                            Text("Absent")
                        }
                    }
                }
            }
        }
    }
}
