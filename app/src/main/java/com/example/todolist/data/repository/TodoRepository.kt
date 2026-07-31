package com.example.todolist.data.repository

import com.example.todolist.data.local.CheckInDao
import com.example.todolist.data.local.TodoDao
import com.example.todolist.data.model.CheckIn
import com.example.todolist.data.model.Todo
import com.example.todolist.util.SampleData
import com.example.todolist.util.todayDateStr
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.atomic.AtomicBoolean

class TodoRepository(
    private val todoDao: TodoDao,
    private val checkInDao: CheckInDao
) {
    val todos: Flow<List<Todo>> = todoDao.observeAll()
    val checkIns: Flow<List<CheckIn>> = checkInDao.observeAll()

    private val seeded = AtomicBoolean(false)

    /** 首次启动写入种子数据（§3.1 / §4 示例） */
    suspend fun ensureSeeded() {
        if (seeded.compareAndSet(false, true)) {
            if (todoDao.count() == 0) todoDao.insertAll(SampleData.todos())
            if (checkInDao.count() == 0) checkInDao.insertAll(SampleData.checkIns())
        }
    }

    suspend fun getTodo(id: Long): Todo? = todoDao.getById(id)

    /** 新建或更新（返回 id） */
    suspend fun upsert(todo: Todo): Long {
        val id = if (todo.id == 0L) todoDao.insert(todo) else {
            todoDao.update(todo)
            todo.id
        }
        reconcileToday()
        return id
    }

    suspend fun delete(todo: Todo) {
        todoDao.delete(todo)
        reconcileToday()
    }

    /**
     * 勾选 / 取消勾选某事项，并维护当日打卡记录（§4.2）。
     * 仅当「勾选后计入今日完成」开启，且用户勾选时，才计入当日打卡。
     */
    suspend fun toggleComplete(todo: Todo, done: Boolean) {
        todoDao.update(todo.copy(completedToday = done))
        reconcileToday()
    }

    /** 按当前各事项状态重算今日打卡计数，写入 checkins 表 */
    private suspend fun reconcileToday() {
        val all = todoDao.getAll()
        val count = all.count { it.completedToday && it.countTowardToday }
        checkInDao.insert(CheckIn(todayDateStr(), count))
    }
}
