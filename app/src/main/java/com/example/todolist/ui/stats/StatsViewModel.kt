package com.example.todolist.ui.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.data.model.CheckIn
import com.example.todolist.data.repository.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StatsViewModel(private val repository: TodoRepository) : ViewModel() {

    val checkIns: StateFlow<List<CheckIn>> = repository.checkIns.stateIn(
        scope = androidx.lifecycle.viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        androidx.lifecycle.viewModelScope.launch { repository.ensureSeeded() }
    }

    companion object {
        fun factory(repo: TodoRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    StatsViewModel(repo) as T
            }
    }
}
