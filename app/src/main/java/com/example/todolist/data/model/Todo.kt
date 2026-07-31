package com.example.todolist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 待办事项实体 —— 对应设计稿"进行中的事项"列表项。
 * 进度% = 已打卡天数 / 总天数（§4.1）
 */
@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val totalDays: Int = 100,
    val checkedDays: Int = 1,
    /** 开关「勾选后计入今日完成」（§4.2），默认开 */
    val countTowardToday: Boolean = true,
    /** 今日是否已勾选 */
    val completedToday: Boolean = false,
    /** 是否开启提醒 */
    val hasReminder: Boolean = false,
    /** 定时提醒 HH:mm:ss（精确到秒，§4.3），默认 14:00:23 */
    val reminderTime: String? = null,
    /** 重复：每天 */
    val repeatDaily: Boolean = true,
    /** 间隔提醒开关 */
    val intervalEnabled: Boolean = false,
    /** 间隔提醒开始时间 HH:mm，默认 14:00 */
    val intervalStart: String? = null,
    /** 间隔数值，默认 15 */
    val intervalValue: Int = 15,
    /** 间隔单位：MINUTE / HOUR，默认 MINUTE */
    val intervalUnit: String = "MINUTE",
    /** 选中铃声 id，默认 water（水滴声） */
    val ringtoneId: String = "water",
    val createdAt: Long = System.currentTimeMillis()
) {
    /** 进度百分比 0..100 */
    val progress: Int
        get() = if (totalDays <= 0) 0
        else ((checkedDays.toFloat() / totalDays) * 100).toInt().coerceIn(0, 100)
}
