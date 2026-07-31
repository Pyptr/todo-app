package com.example.todolist.util

import com.example.todolist.data.model.CheckIn
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
private val weekdayFull = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")

/** 今天日期字符串 yyyy-MM-dd */
fun todayDateStr(): String = fmt.format(Calendar.getInstance().apply { clearTime() }.time)

/** 首页 Header 文案：周六 · 8月1日 */
fun headerDateLabel(): String {
    val cal = Calendar.getInstance()
    val w = weekdayFull[cal.get(Calendar.DAY_OF_WEEK) % 7]
    return "${w} · ${cal.get(Calendar.MONTH) + 1}月${cal.get(Calendar.DAY_OF_MONTH)}日"
}

/** 热力图单元格 */
data class DayCell(
    val date: String,
    val count: Int,
    val future: Boolean
)

/**
 * 构建 GitHub 风格热力图网格：10 列（周） × 7 行（周一~周日）。
 * 最后一列包含今天，未来日期标记 future 不填色。
 */
fun buildHeatmap(checkIns: List<CheckIn>): List<List<DayCell>> {
    val map = checkIns.associateBy { it.date }
    val cal = Calendar.getInstance().apply { clearTime() }
    // 本周一
    val dow = cal.get(Calendar.DAY_OF_WEEK) // 1=Sun .. 7=Sat
    val daysSinceMonday = (dow + 5) % 7
    cal.add(Calendar.DATE, -daysSinceMonday)
    val startMonday = cal.time
    val todayStr = todayDateStr()
    val grid = mutableListOf<List<DayCell>>()
    for (w in 0 until 10) {
        val col = mutableListOf<DayCell>()
        for (d in 0..6) {
            val c = Calendar.getInstance().apply {
                time = startMonday
                add(Calendar.DATE, w * 7 + d)
            }
            val ds = fmt.format(c.time)
            val future = ds > todayStr
            val count = if (future) 0 else (map[ds]?.count ?: 0)
            col.add(DayCell(ds, count, future))
        }
        grid.add(col)
    }
    return grid
}

/** 连续打卡天数（截至今天，连续有打卡的日数） */
fun computeStreak(checkIns: List<CheckIn>): Int {
    val map = checkIns.associateBy { it.date }
    var streak = 0
    val cal = Calendar.getInstance().apply { clearTime() }
    repeat(365) {
        val c = map[fmt.format(cal.time)]?.count ?: 0
        if (c > 0) {
            streak++
            cal.add(Calendar.DATE, -1)
        } else return streak
    }
    return streak
}

/** 本月打卡天数 */
fun computeThisMonth(checkIns: List<CheckIn>): Int {
    val now = Calendar.getInstance()
    val y = now.get(Calendar.YEAR)
    val m = now.get(Calendar.MONTH)
    val p = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return checkIns.count {
        if (it.count <= 0) return@count false
        val c = Calendar.getInstance().apply { time = p.parse(it.date) ?: return@count false }
        c.get(Calendar.YEAR) == y && c.get(Calendar.MONTH) == m
    }
}

/** 将当日打卡数映射为 5 级色阶索引（count=1 → 0 最浅，≥5 → 4 最深） */
fun heatLevel(count: Int): Int = (count - 1).coerceIn(0, 4)

private fun Calendar.clearTime() {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}
