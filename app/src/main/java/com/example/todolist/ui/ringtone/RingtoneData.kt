package com.example.todolist.ui.ringtone

/** 系统铃声定义（§3.4）。id 与 Todo.ringtoneId 对应。 */
data class RingtoneOption(
    val id: String,
    val name: String
)

val SYSTEM_RINGTONES = listOf(
    RingtoneOption("water", "水滴声"),
    RingtoneOption("bird", "鸟鸣"),
    RingtoneOption("crisp", "清脆"),
    RingtoneOption("soft", "柔和")
)

fun ringtoneName(id: String): String =
    SYSTEM_RINGTONES.firstOrNull { it.id == id }?.name ?: "水滴声"
