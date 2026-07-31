package com.example.todolist

import android.app.Application
import com.example.todolist.data.local.AppDatabase
import com.example.todolist.data.repository.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy {
        TodoRepository(database.todoDao(), database.checkInDao())
    }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            repository.ensureSeeded()
        }
    }
}
