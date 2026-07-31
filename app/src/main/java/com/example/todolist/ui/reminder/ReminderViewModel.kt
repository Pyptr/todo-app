package com.example.todolist.ui.reminder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.model.Todo
import com.example.todolist.data.repository.TodoRepository
import kotlinx.coroutines.launch

class ReminderViewModel(
    private val todoId: Long,
    private val repository: TodoRepository
) : ViewModel() {

    var hasReminder by mutableStateOf(false)
    var hour by mutableStateOf("14")
    var minute by mutableStateOf("00")
    var second by mutableStateOf("23")
    var repeatDaily by mutableStateOf(true)

    var intervalEnabled by mutableStateOf(false)
    var intervalHour by mutableStateOf("14")
    var intervalMinute by mutableStateOf("00")
    var intervalValue by mutableStateOf("15")
    var intervalUnit by mutableStateOf("MINUTE") // MINUTE | HOUR

    var ringtoneId by mutableStateOf("water")

    var loaded by mutableStateOf(false)
        private set

    private var original: Todo? = null

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
            if (todoId > 0) {
                repository.getTodo(todoId)?.let { t ->
                    original = t
                    hasReminder = t.hasReminder
                    t.reminderTime?.split(":")?.let {
                        if (it.size == 3) {
                            hour = it[0]; minute = it[1]; second = it[2]
                        }
                    }
                    repeatDaily = t.repeatDaily
                    intervalEnabled = t.intervalEnabled
                    t.intervalStart?.split(":")?.let {
                        if (it.size == 2) { intervalHour = it[0]; intervalMinute = it[1] }
                    }
                    intervalValue = t.intervalValue.toString()
                    intervalUnit = t.intervalUnit
                    ringtoneId = t.ringtoneId
                }
            }
            loaded = true
        }
    }

    fun save(onSaved: () -> Unit) = viewModelScope.launch {
        val t = original ?: return@launch
        val updated = t.copy(
            hasReminder = hasReminder,
            reminderTime = if (hasReminder) "$hour:$minute:$second" else t.reminderTime,
            repeatDaily = repeatDaily,
            intervalEnabled = intervalEnabled,
            intervalStart = if (intervalEnabled) "$intervalHour:$intervalMinute" else null,
            intervalValue = intervalValue.toIntOrNull() ?: 15,
            intervalUnit = intervalUnit,
            ringtoneId = ringtoneId
        )
        repository.upsert(updated)
        onSaved()
    }

    companion object {
        fun factory(todoId: Long, repo: TodoRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    ReminderViewModel(todoId, repo) as T
            }
    }
}
