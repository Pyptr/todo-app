package com.example.todolist.ui.ringtone

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.util.viewModelScope
import com.example.todolist.data.model.Todo
import com.example.todolist.data.repository.TodoRepository
import kotlinx.coroutines.launch

data class SearchSong(
    val id: String,
    val name: String,
    val artist: String? = null
)

class RingtoneViewModel(
    private val todoId: Long,
    private val repository: TodoRepository
) : ViewModel() {

    var selectedId by mutableStateOf("water")
    var searchQuery by mutableStateOf("")
    var results by mutableStateOf<List<SearchSong>>(emptyList())
    var downloaded by mutableStateOf<Set<String>>(emptySet())
    var isPlaying by mutableStateOf(false)
    var loaded by mutableStateOf(false)
        private set

    private var original: Todo? = null

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
            if (todoId > 0) {
                repository.getTodo(todoId)?.let { t ->
                    original = t
                    selectedId = t.ringtoneId
                }
            }
            loaded = true
        }
    }

    /** 在线搜歌（§4.5）。此处为演示占位：返回与关键词相关的示例结果。
     *  接入真实开源免费 API（如 Deezer / iTunes Search，无需 token）时替换此函数即可。 */
    fun setQuery(q: String) {
        searchQuery = q
        results = if (q.isBlank()) {
            emptyList()
        } else {
            listOf(
                SearchSong("local_${q}", q, null),
                SearchSong("deezer_1", "River Flows in You", "Yiruma"),
                SearchSong("deezer_2", "晴天", "周杰伦")
            )
        }
    }

    fun select(id: String) {
        selectedId = id
    }

    fun togglePlay() {
        isPlaying = !isPlaying
    }

    fun download(id: String) {
        downloaded = downloaded + id
    }

    fun save(onSaved: () -> Unit) = viewModelScope.launch {
        val t = original ?: return@launch
        repository.upsert(t.copy(ringtoneId = selectedId))
        onSaved()
    }

    companion object {
        fun factory(todoId: Long, repo: TodoRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    RingtoneViewModel(todoId, repo) as T
            }
    }
}
