package com.example.todolist.util

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

/**
 * Self-contained re-implementation of AndroidX `viewModelScope`.
 *
 * The `androidx.lifecycle:lifecycle-viewmodel-ktx` artifact intermittently fails to
 * expose `viewModelScope` on the compile classpath under certain Gradle/Kotlin
 * combinations, so we define an identical extension here to guarantee compilation.
 * Semantics match AndroidX: a SupervisorJob-backed scope on Dispatchers.Main.immediate,
 * stored on the ViewModel via the public `addCloseable`/`getCloseable` API and cancelled
 * automatically when the ViewModel is cleared.
 */

private const val SCOPE_KEY = "com.example.todolist.util.viewModelScope"

val ViewModel.viewModelScope: CoroutineScope
    get() {
        val existing = getCloseable(SCOPE_KEY) as? CoroutineScope
        if (existing != null) return existing
        val scope = CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        addCloseable(SCOPE_KEY, scope)
        return scope
    }

private class CloseableCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context
    override fun close() {
        coroutineContext.cancel()
    }
}
