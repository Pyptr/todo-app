package com.example.todolist.ui.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.model.Todo
import com.example.todolist.data.repository.TodoRepository
import kotlinx.coroutines.launch

class EditTodoViewModel(
    private val todoId: Long,
    private val repository: TodoRepository
) : ViewModel() {

    var name by mutableStateOf("")
    var totalDays by mutableStateOf("100")
    var checkedDays by mutableStateOf("1")
    var countTowardToday by mutableStateOf(true)
    var loaded by mutableStateOf(false)
        private set

    private var original: Todo? = null

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
            if (todoId > 0) {
                repository.getTodo(todoId)?.let { t ->
                    original = t
                    name = t.name
                    totalDays = t.totalDays.toString()
                    checkedDays = t.checkedDays.toString()
                    countTowardToday = t.countTowardToday
                }
            }
            loaded = true
        }
    }

    /** 实时进度预览（§4.1） */
    val progress: Int
        get() {
            val tot = totalDays.toIntOrNull() ?: 0
            val chk = checkedDays.toIntOrNull() ?: 0
            return if (tot <= 0) 0 else (chk.toFloat() / tot * 100).toInt().coerceIn(0, 100)
        }

    fun save(onSaved: () -> Unit) = viewModelScope.launch {
        saveAndGetId()
        onSaved()
    }

    /** 保存并返回新 id（供先存草稿再进入提醒设置使用） */
    suspend fun saveAndGetId(): Long {
        val tot = (totalDays.toIntOrNull() ?: 100).coerceAtLeast(1)
        val chk = (checkedDays.toIntOrNull() ?: 1).coerceIn(0, tot)
        val t = original
        val todo = if (t == null) {
            Todo(
                name = name,
                totalDays = tot,
                checkedDays = chk,
                countTowardToday = countTowardToday
            )
        } else {
            t.copy(
                name = name,
                totalDays = tot,
                checkedDays = chk,
                countTowardToday = countTowardToday
            )
        }
        val id = repository.upsert(todo)
        original = repository.getTodo(id)
        return id
    }

    companion object {
        fun factory(todoId: Long, repo: TodoRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    EditTodoViewModel(todoId, repo) as T
            }
    }
}
