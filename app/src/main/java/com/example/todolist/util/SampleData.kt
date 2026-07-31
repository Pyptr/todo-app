package com.example.todolist.util

import com.example.todolist.data.model.CheckIn
import com.example.todolist.data.model.Todo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Random

/**
 * 首次启动的种子数据，对齐设计稿示例（§3.1 / §4）。
 * - 待办：阅读 30 分钟(12/100)、健身打卡(45/100·已勾选)、背单词 50 个(1/100)
 * - 打卡记录：近 70 天，最后 7 天连续有打卡（连续打卡 = 7），强度随机铺满 5 级。
 */
object SampleData {

    fun todos(): List<Todo> = listOf(
        Todo(
            name = "阅读 30 分钟",
            totalDays = 100,
            checkedDays = 12,
            countTowardToday = true,
            completedToday = false,
            hasReminder = true,
            reminderTime = "14:00:23",
            ringtoneId = "water"
        ),
        Todo(
            name = "健身打卡",
            totalDays = 100,
            checkedDays = 45,
            countTowardToday = true,
            completedToday = true,
            hasReminder = true,
            reminderTime = "07:30:00",
            ringtoneId = "bird"
        ),
        Todo(
            name = "背单词 50 个",
            totalDays = 100,
            checkedDays = 1,
            countTowardToday = true,
            completedToday = false,
            hasReminder = false,
            ringtoneId = "water"
        )
    )

    fun checkIns(): List<CheckIn> {
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val today = cal.time
        val rnd = Random(42)
        val list = mutableListOf<CheckIn>()
        for (i in 69 downTo 0) {
            val d = Calendar.getInstance().apply {
                time = today
                add(Calendar.DATE, -i)
            }
            val dateStr = fmt.format(d.time)
            val count = if (i < 7) {
                rnd.nextInt(3) + 2 // 最后 7 天连续打卡，强度 2..4
            } else {
                if (rnd.nextFloat() < 0.62f) rnd.nextInt(5) else 0
            }
            list.add(CheckIn(dateStr, count))
        }
        return list
    }
}
